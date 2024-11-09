package cleverton.heusner.adapter.output.provider.address;

import cleverton.heusner.adapter.output.mapper.AddressResponseMapper;
import cleverton.heusner.adapter.output.response.AddressResponse;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.exception.UnavailableResourceException;
import cleverton.heusner.domain.model.Address;
import cleverton.heusner.port.output.provider.address.AddressProvider;
import cleverton.heusner.port.shared.LoggerComponent;
import cleverton.heusner.port.shared.MessageComponent;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static cleverton.heusner.adapter.input.constant.business.AddressBusinessErrorMessage.NOT_FOUND_ADDRESS_PROVIDER_MESSAGE;
import static cleverton.heusner.adapter.input.constant.business.AddressBusinessErrorMessage.UNAVAILABLE_ADDRESS_PROVIDER_MESSAGE;
import static cleverton.heusner.adapter.output.configuration.AddressProviderConfiguration.ADDRESS_RETRY;

@Service
public class AddressProviderImpl implements AddressProvider {

    private static final ThreadLocal<String> currentZipCode = new ThreadLocal<>();

    private final String addressProviderUrl;
    private final AddressResponseMapper addressResponseMapper;
    private final MessageComponent messageComponent;
    private final LoggerComponent logger;
    private final RestTemplate restTemplate;
    private final io.github.resilience4j.retry.Retry retry;

    public AddressProviderImpl(@Value("${user.address-provider.uri}") final String addressProviderUrl,
                               final AddressResponseMapper addressResponseMapper,
                               final MessageComponent messageComponent,
                               final LoggerComponent logger,
                               final RestTemplate restTemplate,
                               final io.github.resilience4j.retry.Retry retry) {
        this.addressProviderUrl = addressProviderUrl;
        this.addressResponseMapper = addressResponseMapper;
        this.messageComponent = messageComponent;
        this.logger = logger;
        this.restTemplate = restTemplate;
        this.retry = retry;
    }

    @PostConstruct
    public void listenCompleteAddressRetrievingRetries() {
        retry.getEventPublisher().onRetry(this::onRetryEvent);
    }

    @Retry(name = ADDRESS_RETRY, fallbackMethod = "retrieveIncompleteAddress")
    public Address getAddressByZipCode(final Address address) {
        currentZipCode.set(address.getZipCode());
        final ResponseEntity<AddressResponse> response = requestAddressByZipCode(address.getZipCode());

        logger.info("Complete address for ZIP code '%' successfully retrieved.", address.getZipCode());
        assert response != null;
        return addressResponseMapper.toModel(response.getBody(), address.asComplete());
    }

    private ResponseEntity<AddressResponse> requestAddressByZipCode(final String zipCode) {

        try {
            return restTemplate.getForEntity(createAddressProviderUri(zipCode), AddressResponse.class);
        }
        catch (final HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException(messageComponent.getMessage(NOT_FOUND_ADDRESS_PROVIDER_MESSAGE));
            }

            if (e.getStatusCode().is5xxServerError()) {
                throw new UnavailableResourceException(messageComponent.getMessage(UNAVAILABLE_ADDRESS_PROVIDER_MESSAGE));
            }
        }

        return null;
    }

    private URI createAddressProviderUri(final String zipCode) {
        return UriComponentsBuilder.fromUriString(addressProviderUrl).build(zipCode);
    }

    private Address retrieveIncompleteAddress(final Address address, final Throwable throwable) {
        rethrowIfNotFoundException(throwable);

        logger.warn("Failed to retrieve complete address for ZIP code '%'. Returning incomplete address. Error: %",
                address.getZipCode(), throwable.getMessage());
        return address.asIncomplete();
    }

    private void rethrowIfNotFoundException(final Throwable throwable) {
        if (throwable instanceof ResourceNotFoundException e) {
            throw e;
        }
    }

    private void onRetryEvent(final RetryOnRetryEvent event) {
        final Throwable lastThrowable = event.getLastThrowable();

        logger.info(
                "Retry '%' to retrieve the address for ZIP code '%' using the retry '%'. Last exception: %",
                event.getNumberOfRetryAttempts(),
                currentZipCode.get(),
                event.getName(),
                lastThrowable != null ? lastThrowable.getMessage() : "no exception."
        );
    }
}
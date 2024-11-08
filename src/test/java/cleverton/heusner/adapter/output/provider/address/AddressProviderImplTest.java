package cleverton.heusner.adapter.output.provider.address;

import cleverton.heusner.adapter.output.mapper.AddressResponseMapper;
import cleverton.heusner.adapter.output.response.AddressResponse;
import cleverton.heusner.domain.exception.UnavailableResourceException;
import cleverton.heusner.domain.model.Address;
import cleverton.heusner.port.shared.LoggerComponent;
import cleverton.heusner.port.shared.MessageComponent;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static cleverton.heusner.adapter.input.constant.business.AddressBusinessErrorMessage.UNAVAILABLE_ADDRESS_PROVIDER_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressProviderImplTest {

    @Mock
    private AddressResponseMapper addressResponseMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Retry retry;

    @Mock
    private Retry.EventPublisher eventPublisher;

    @Mock
    private RetryOnRetryEvent retryOnRetryEvent;

    @Mock
    private MessageComponent messageComponent;

    @Mock
    private LoggerComponent loggerComponent;

    @InjectMocks
    private AddressProviderImpl addressProvider;

    private static final String ADDRESS_PROVIDER_URL = "http://mock-address-provider";
    private URI uri;

    @BeforeEach
    void setUp() {
        uri = URI.create(ADDRESS_PROVIDER_URL);
        ReflectionTestUtils.setField(addressProvider, "addressProviderUrl", ADDRESS_PROVIDER_URL);
    }

    @Test
    void when_completeAddressIsQueriedByCep_then_completeAddressIsRetrieved() {

        // Arrange
        final String zipCode = "12345678";
        final Address incompleteAddress = Instancio.of(Address.class)
                .set(Select.field(Address::getZipCode), zipCode)
                .set(Select.field(Address::isComplete), false)
                .create();
        final AddressResponse addressResponse = Instancio.create(AddressResponse.class);
        final Address expectedCompleteAddress = Instancio.of(Address.class)
                .set(Select.field(Address::isComplete), true)
                .create();
        final ResponseEntity<AddressResponse> responseEntity = new ResponseEntity<>(addressResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, AddressResponse.class)).thenReturn(responseEntity);
        when(addressResponseMapper.toModel(addressResponse, incompleteAddress)).thenReturn(expectedCompleteAddress);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final Address actualCompleteAddress = addressProvider.getAddressByZipCode(incompleteAddress);

        // Assert
        assertThat(actualCompleteAddress).isEqualTo(expectedCompleteAddress);

        verify(restTemplate).getForEntity(uri, AddressResponse.class);
        verify(addressResponseMapper).toModel(addressResponse, incompleteAddress);
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }

    @Test
    void when_addressProviderIsUnavailable_then_throwUnavailableResourceException() {

        // Arrange
        final String expectedUnavailableAddressProviderMessage = "Address provider service is currently unavailable";
        final String zipCode = "12345678";
        final Address incompleteAddress = Instancio.of(Address.class)
                .set(Select.field(Address::getZipCode), zipCode)
                .set(Select.field(Address::isComplete), false)
                .create();
        final ResponseEntity<AddressResponse> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(messageComponent.getMessage(UNAVAILABLE_ADDRESS_PROVIDER_MESSAGE)).thenReturn(
                expectedUnavailableAddressProviderMessage
        );
        when(restTemplate.getForEntity(uri, AddressResponse.class)).thenReturn(responseEntity);

        // Act & Assert
        assertThatThrownBy(() -> addressProvider.getAddressByZipCode(incompleteAddress))
                .isInstanceOf(UnavailableResourceException.class)
                .hasMessage(expectedUnavailableAddressProviderMessage);

        verify(messageComponent).getMessage(UNAVAILABLE_ADDRESS_PROVIDER_MESSAGE);
        verify(restTemplate).getForEntity(any(URI.class), eq(AddressResponse.class));
        verifyNoInteractions(addressResponseMapper);
    }
}
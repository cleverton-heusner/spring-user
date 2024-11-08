package cleverton.heusner.adapter.output.configuration;

import cleverton.heusner.domain.exception.BusinessException;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AddressProviderConfiguration {

    public static final String ADDRESS_RETRY = "addressRetry";

    private static final double BACKOFF_FACTOR = 2.0;
    private static final int INITIAL_WAIT_IN_MLS = 500;
    private static final int MAX_TRIES = 4;

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RetryRegistry retryRegistry() {
        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_TRIES)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(INITIAL_WAIT_IN_MLS, BACKOFF_FACTOR))
                .ignoreExceptions(BusinessException.class)
                .build();

        return RetryRegistry.of(retryConfig);
    }

    @Bean
    public Retry addressRetry(final RetryRegistry retryRegistry) {
        return retryRegistry.retry(ADDRESS_RETRY);
    }
}
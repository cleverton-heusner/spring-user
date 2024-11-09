package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.service.login.LoginServiceImpl;
import cleverton.heusner.port.input.service.login.LoginService;
import cleverton.heusner.port.output.provider.login.LoginProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginServiceConfiguration {

    @Bean
    public LoginService loginService(final LoginProvider loginProvider) {
        return new LoginServiceImpl(loginProvider);
    }
}
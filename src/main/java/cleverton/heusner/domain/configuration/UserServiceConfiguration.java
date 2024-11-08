package cleverton.heusner.domain.configuration;

import cleverton.heusner.domain.service.login.LoginService;
import cleverton.heusner.domain.service.user.UserServiceImpl;
import cleverton.heusner.port.input.service.user.UserService;
import cleverton.heusner.port.output.provider.address.AddressProvider;
import cleverton.heusner.port.output.provider.user.UserProvider;
import cleverton.heusner.port.shared.LoggerComponent;
import cleverton.heusner.port.shared.MessageComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {

    @Bean
    public UserService userService(final UserProvider userProvider,
                                   final AddressProvider addressProvider,
                                   final MessageComponent messageComponent,
                                   final LoggerComponent loggerComponent,
                                   final LoginService loginService) {
        return new UserServiceImpl(userProvider, addressProvider, messageComponent, loggerComponent, loginService);
    }
}
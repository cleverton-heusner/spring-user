package cleverton.heusner.domain.service.login;

import cleverton.heusner.adapter.output.entity.login.LoginEntity;
import cleverton.heusner.port.input.service.login.LoginService;
import cleverton.heusner.port.output.provider.login.LoginProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class LoginServiceImpl implements LoginService {

    private final LoginProvider loginProvider;

    public LoginServiceImpl(final LoginProvider loginProvider) {
        this.loginProvider = loginProvider;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return loginProvider.loadUserByUsername(username);
    }

    @Override
    public LoginEntity register(final LoginEntity loginEntity) {
        return loginProvider.register(loginEntity);
    }

    @Override
    public List<LoginEntity> findAll() {
        return loginProvider.findAll();
    }
}
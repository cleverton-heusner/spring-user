package cleverton.heusner.adapter.output.provider.login;

import cleverton.heusner.adapter.output.entity.login.LoginEntity;
import cleverton.heusner.adapter.output.repository.LoginRepository;
import cleverton.heusner.port.output.provider.login.LoginProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginProviderImpl implements LoginProvider {

    private final LoginRepository loginRepository;

    public LoginProviderImpl(final LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return loginRepository.findByUsername(username);
    }

    @Override
    public LoginEntity register(final LoginEntity loginEntity) {
        return loginRepository.save(loginEntity);
    }

    @Override
    public List<LoginEntity> findAll() {
        return loginRepository.findAll();
    }
}
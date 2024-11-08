package cleverton.heusner.domain.service.login;

import cleverton.heusner.adapter.output.repository.LoginRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    public LoginServiceImpl(final LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return loginRepository.findByUsername(username);
    }

    @Override
    public String retrieveLoginUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
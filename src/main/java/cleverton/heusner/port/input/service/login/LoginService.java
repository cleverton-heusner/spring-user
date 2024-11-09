package cleverton.heusner.port.input.service.login;

import cleverton.heusner.adapter.output.entity.login.LoginEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface LoginService extends UserDetailsService {

    UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException;
    LoginEntity register(final LoginEntity loginEntity);
    List<LoginEntity> findAll();
}
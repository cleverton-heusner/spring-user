package cleverton.heusner.domain.service.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginService extends UserDetailsService {
    UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException;
    String retrieveLoginUsername();
}
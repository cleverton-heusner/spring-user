package cleverton.heusner.port.output.provider.login;

import cleverton.heusner.adapter.output.entity.login.LoginEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface LoginProvider {

    UserDetails loadUserByUsername(final String username);
    LoginEntity register(final LoginEntity loginEntity);
    List<LoginEntity> findAll();
}
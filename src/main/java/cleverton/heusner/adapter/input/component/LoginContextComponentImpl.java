package cleverton.heusner.adapter.input.component;

import cleverton.heusner.port.input.component.LoginContextComponent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginContextComponentImpl implements LoginContextComponent {

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
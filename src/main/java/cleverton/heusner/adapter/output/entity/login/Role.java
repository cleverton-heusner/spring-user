package cleverton.heusner.adapter.output.entity.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("admin"),
    USER("user");

    private final String role;
}

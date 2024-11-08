package cleverton.heusner.port.output.provider.user;

import cleverton.heusner.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserProvider {

    Optional<User> findById(final Long id);
    User save(final User user);
    List<User> findAll();
    List<User> filterByNameOrZipCode(final String nameOrZipCode);
    Optional<User> findByCpf(final String cpf);
}
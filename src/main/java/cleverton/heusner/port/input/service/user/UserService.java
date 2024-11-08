package cleverton.heusner.port.input.service.user;

import cleverton.heusner.domain.model.User;

import java.util.List;

public interface UserService {

    User findByCpf(final String id);
    User findById(final String id);
    User register(final User user);
    User updateById(final Long id, final User user);
    User activateOrDeactivateById(final String id, final boolean activation);
    List<User> filterByNameOrZipCode(final String nameOrZipCode);
    List<User> findAll();
}

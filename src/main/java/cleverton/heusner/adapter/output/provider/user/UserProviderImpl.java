package cleverton.heusner.adapter.output.provider.user;

import cleverton.heusner.adapter.output.entity.user.UserEntity;
import cleverton.heusner.adapter.output.mapper.UserEntityMapper;
import cleverton.heusner.adapter.output.repository.UserRepository;
import cleverton.heusner.domain.model.User;
import cleverton.heusner.port.output.provider.user.UserProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProviderImpl implements UserProvider {

    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;

    public UserProviderImpl(final UserEntityMapper userEntityMapper,
                            final UserRepository userRepository) {
        this.userEntityMapper = userEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(final Long id) {
        final Optional<UserEntity> userFoundOptional = userRepository.findById(id);
        return userFoundOptional.map(userEntityMapper::toModel);
    }

    @Override
    public Optional<User> findByCpf(final String cpf) {
        final Optional<UserEntity> userFoundOptional = userRepository.findByCpf(cpf);
        return userFoundOptional.map(userEntityMapper::toModel);
    }

    @Override
    public User save(final User user) {
        final UserEntity savedUser = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toModel(savedUser);
    }

    @Override
    public List<User> filterByNameOrZipCode(final String nameOrZipCode) {
        return userRepository.filterByNameOrZipCode(nameOrZipCode)
                .stream()
                .map(userEntityMapper::toModel)
                .toList();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(userEntityMapper::toModel).toList();
    }
}
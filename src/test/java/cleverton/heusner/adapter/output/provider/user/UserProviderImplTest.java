package cleverton.heusner.adapter.output.provider.user;

import cleverton.heusner.adapter.output.entity.UserEntity;
import cleverton.heusner.adapter.output.mapper.UserEntityMapper;
import cleverton.heusner.adapter.output.repository.UserRepository;
import cleverton.heusner.domain.model.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProviderImplTest {

    @Mock
    private UserEntityMapper userEntityMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProviderImpl userProviderImpl;

    @Test
    void when_existingUserIsQueriedById_then_userIsRetrieved() {

        // Arrange
        final Long userId = 1L;
        final UserEntity userEntity = Instancio.create(UserEntity.class);
        final User expectedUser = Instancio.create(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toModel(userEntity)).thenReturn(expectedUser);

        // Act
        final Optional<User> actualUserOptional = userProviderImpl.findById(userId);

        // Assert
        assertThat(actualUserOptional).contains(expectedUser);

        verify(userRepository).findById(userId);
        verify(userEntityMapper).toModel(userEntity);
    }

    @Test
    void when_nonExistingUserIsQueriedById_then_throwResourceNotFoundException() {

        // Arrange
        final long userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        final Optional<User> actualUserOptional = userProviderImpl.findById(userId);

        // Assert
        assertThat(actualUserOptional).isEmpty();

        verify(userRepository).findById(userId);
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    void when_existingUserIsQueriedByCpf_then_userIsRetrieved() {

        // Arrange
        final String userCpf = "12345678901";
        final UserEntity userEntity = Instancio.create(UserEntity.class);
        final User expectedUser = Instancio.create(User.class);

        when(userRepository.findByCpf(userCpf)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toModel(userEntity)).thenReturn(expectedUser);

        // Act
        final Optional<User> actualUserOptional = userProviderImpl.findByCpf(userCpf);

        // Assert
        assertThat(actualUserOptional).contains(expectedUser);

        verify(userRepository).findByCpf(userCpf);
        verify(userEntityMapper).toModel(userEntity);
    }

    @Test
    void when_nonExistingUserIsQueriedByCpf_then_throwResourceNotFoundException() {

        // Arrange
        final String userCpf = "12345678901";
        when(userRepository.findByCpf(userCpf)).thenReturn(Optional.empty());

        // Act
        final Optional<User> actualUserOptional = userProviderImpl.findByCpf(userCpf);

        // Assert
        assertThat(actualUserOptional).isEmpty();

        verify(userRepository).findByCpf(userCpf);
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    void when_saveNewUser_then_userIsSaved() {

        // Arrange
        final User expectedUser = Instancio.create(User.class);
        final UserEntity userEntity = Instancio.create(UserEntity.class);
        final UserEntity savedUserEntity = Instancio.create(UserEntity.class);
        final User savedUser = Instancio.create(User.class);

        when(userEntityMapper.toEntity(expectedUser)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(userEntityMapper.toModel(savedUserEntity)).thenReturn(savedUser);

        // Act
        final User actualUser = userProviderImpl.save(expectedUser);

        // Assert
        assertThat(actualUser).isEqualTo(savedUser);

        verify(userEntityMapper).toEntity(expectedUser);
        verify(userRepository).save(userEntity);
        verify(userEntityMapper).toModel(savedUserEntity);
    }

    @Test
    void when_filterByNameOrZipCode_then_returnMatchingUsers() {

        // Arrange
        final int usersSize = 2;
        final String query = "someNameOrZip";
        final List<UserEntity> userEntities = Instancio.ofList(UserEntity.class).size(usersSize).create();
        final List<User> expectedUsers = Instancio.ofList(User.class).size(usersSize).create();

        when(userRepository.filterByNameOrZipCode(query)).thenReturn(userEntities);
        IntStream.range(0, usersSize)
                .forEach(i -> when(userEntityMapper.toModel(userEntities.get(i))).thenReturn(expectedUsers.get(i)));

        // Act
        final List<User> actualUsers = userProviderImpl.filterByNameOrZipCode(query);

        // Assert
        assertThat(actualUsers).isEqualTo(expectedUsers);

        verify(userRepository).filterByNameOrZipCode(query);
        verify(userEntityMapper, times(usersSize)).toModel(any(UserEntity.class));
    }

    @Test
    void when_allUsersAreQueried_then_returnListOfUsers() {

        // Arrange
        final int usersSize = 2;
        final List<UserEntity> userEntities = Instancio.ofList(UserEntity.class).size(usersSize).create();
        final List<User> expectedUsers = Instancio.ofList(User.class).size(usersSize).create();

        when(userRepository.findAll()).thenReturn(userEntities);
        IntStream.range(0, usersSize)
                .forEach(i -> when(userEntityMapper.toModel(userEntities.get(i))).thenReturn(expectedUsers.get(i)));

        // Act
        final List<User> actualUsers = userProviderImpl.findAll();

        // Assert
        assertThat(actualUsers).isEqualTo(expectedUsers);

        verify(userRepository).findAll();
        verify(userEntityMapper, times(usersSize)).toModel(any(UserEntity.class));
    }
}
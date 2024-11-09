package cleverton.heusner.domain.service;

import cleverton.heusner.domain.exception.BusinessException;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.model.User;
import cleverton.heusner.domain.service.user.UserServiceImpl;
import cleverton.heusner.port.input.component.LoginContextComponent;
import cleverton.heusner.port.output.provider.address.AddressProvider;
import cleverton.heusner.port.output.provider.user.UserProvider;
import cleverton.heusner.port.shared.LoggerComponent;
import cleverton.heusner.port.shared.MessageComponent;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static cleverton.heusner.adapter.input.constant.business.UserBusinessErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserProvider userProvider;

    @Mock
    private AddressProvider addressProvider;

    @Mock
    private MessageComponent messageComponent;

    @Mock
    private LoggerComponent loggerComponent;

    @Mock
    private LoginContextComponent loginContext;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void when_existingUserIsQueriedById_then_userIsRetrieved() {

        // Arrange
        final var expectedUser = Instancio.create(User.class);
        final String userId = String.valueOf(expectedUser.getId());

        when(userProvider.findById(Long.valueOf(userId))).thenReturn(Optional.of(expectedUser));
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final User actualUser = userServiceImpl.findById(userId);

        // Assert
        assertThat(actualUser).isEqualTo(expectedUser);

        verify(userProvider).findById(Long.valueOf(userId));
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }

    @Test
    void when_nonExistingUserIsQueriedById_then_throwResourceNotFoundException() {

        // Arrange
        final String userId = "123";
        when(userProvider.findById(Long.valueOf(userId))).thenReturn(Optional.empty());
        when(messageComponent.getMessage(USER_NOT_FOUND_BY_ID_MESSAGE, userId)).thenReturn("User not found");
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));
        doNothing().when(loggerComponent).error(anyString(), any(Object[].class));

        // Act & Assert
        assertThatThrownBy(() -> userServiceImpl.findById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");

        verify(userProvider).findById(Long.valueOf(userId));
        verify(messageComponent).getMessage(USER_NOT_FOUND_BY_ID_MESSAGE, userId);
        verify(loggerComponent).info(anyString(), any(Object[].class));
        verify(loggerComponent).error(anyString(), any(Object[].class));
    }

    @Test
    void when_existingUserIsQueriedByCpf_then_userIsRetrieved() {

        // Arrange
        final var expectedUser = Instancio.create(User.class);
        final String userCpf = expectedUser.getCpf();

        when(userProvider.findByCpf(userCpf)).thenReturn(Optional.of(expectedUser));
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final User actualUser = userServiceImpl.findByCpf(userCpf);

        // Assert
        assertThat(actualUser).isEqualTo(expectedUser);
        verify(userProvider).findByCpf(userCpf);
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }

    @Test
    void when_nonExistingUserIsQueriedByCpf_then_throwResourceNotFoundException() {

        // Arrange
        final String userCpf = "33306418005";
        when(userProvider.findByCpf(userCpf)).thenReturn(Optional.empty());
        when(messageComponent.getMessage(USER_NOT_FOUND_BY_CPF_MESSAGE, userCpf)).thenReturn("User not found");
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));
        doNothing().when(loggerComponent).error(anyString(), any(Object[].class));

        // Act & Assert
        assertThatThrownBy(() -> userServiceImpl.findByCpf(userCpf))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");

        verify(userProvider).findByCpf(userCpf);
        verify(messageComponent).getMessage(USER_NOT_FOUND_BY_CPF_MESSAGE, userCpf);
        verify(loggerComponent).info(anyString(), any(Object[].class));
        verify(loggerComponent).error(anyString(), any(Object[].class));
    }

    @Test
    void when_registerNewUser_then_userIsSaved() {

        // Arrange
        final var expectedSavedUser = Instancio.of(User.class)
                .set(Select.field(User::getBirthDate), LocalDate.now())
                .create();

        when(userProvider.findByCpf(expectedSavedUser.getCpf())).thenReturn(Optional.empty());
        when(addressProvider.getAddressByZipCode(expectedSavedUser.getAddress())).thenReturn(expectedSavedUser.getAddress());
        when(userProvider.save(expectedSavedUser)).thenReturn(expectedSavedUser);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final User actualSavedUser = userServiceImpl.register(expectedSavedUser);

        // Assert
        assertThat(actualSavedUser).isEqualTo(expectedSavedUser);

        verify(userProvider).findByCpf(expectedSavedUser.getCpf());
        verify(addressProvider).getAddressByZipCode(expectedSavedUser.getAddress());
        verify(userProvider).save(expectedSavedUser);
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }

    @Test
    void when_registerUserWithExistingCpf_then_throwExistingResourceException() {

        // Arrange
        final var existingUser = Instancio.of(User.class)
                .set(Select.field(User::getBirthDate), LocalDate.now())
                .create();

        when(userProvider.findByCpf(existingUser.getCpf())).thenReturn(Optional.of(existingUser));
        when(messageComponent.getMessage(USER_EXISTING_MESSAGE, existingUser.getCpf())).thenReturn("User already exists");
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));
        doNothing().when(loggerComponent).error(anyString(), any(Object[].class));

        // Act & Assert
        assertThatThrownBy(() -> userServiceImpl.register(existingUser))
                .isInstanceOf(ExistingResourceException.class)
                .hasMessage("User already exists");

        verify(userProvider).findByCpf(existingUser.getCpf());
        verify(messageComponent).getMessage(USER_EXISTING_MESSAGE, existingUser.getCpf());
        verify(loggerComponent).info(anyString(), any(Object[].class));
        verify(loggerComponent).error(anyString(), any(Object[].class));
    }

    @Test
    void when_activateExistingUser_then_userActivationStatusIsUpdated() {

        // Arrange
        final var inactiveUser = Instancio.of(User.class)
                .set(Select.field(User::isActive), false)
                .create();
        final String userId = String.valueOf(inactiveUser.getId());
        final boolean expectedActivation = true;

        when(userProvider.findById(Long.valueOf(userId))).thenReturn(Optional.of(inactiveUser));
        when(userProvider.save(inactiveUser)).thenReturn(inactiveUser);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final boolean actualActivation = userServiceImpl.activateOrDeactivateById(userId, expectedActivation).isActive();

        // Assert
        assertThat(actualActivation).isEqualTo(expectedActivation);

        verify(userProvider).findById(Long.valueOf(userId));
        verify(userProvider).save(inactiveUser);
        verify(loggerComponent, times(2)).info(anyString(), any(Object[].class));
    }

    @Test
    void when_deactivateExistingUser_then_userActivationStatusIsUpdated() {

        // Arrange
        final var ativeUser = Instancio.of(User.class)
                .set(Select.field(User::isActive), true)
                .create();
        final String userId = String.valueOf(ativeUser.getId());
        final boolean expectedActivation = false;

        when(userProvider.findById(Long.valueOf(userId))).thenReturn(Optional.of(ativeUser));
        when(userProvider.save(ativeUser)).thenReturn(ativeUser);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));
        when(loginContext.getUserName()).thenReturn("loginUserName");

        // Act
        final boolean actualActivation = userServiceImpl.activateOrDeactivateById(userId, expectedActivation).isActive();

        // Assert
        assertThat(actualActivation).isEqualTo(expectedActivation);

        verify(userProvider).findById(Long.valueOf(userId));
        verify(userProvider).save(ativeUser);
        verify(loggerComponent, times(2)).info(anyString(), any(Object[].class));
        verify(loginContext).getUserName();
    }

    @Test
    void when_birthDateIsInFuture_then_throwBusinessException() {

        // Arrange
        final var userWithFutureBirthDate = Instancio.of(User.class)
                .set(Select.field(User::getBirthDate), LocalDate.now().plusYears(1))
                .create();
        when(messageComponent.getMessage(USER_BIRTH_DATE_IN_FUTURE)).thenReturn("Birth date is in the future");
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));
        doNothing().when(loggerComponent).error(anyString(), any(Object[].class));

        // Act & Assert
        assertThatThrownBy(() -> userServiceImpl.register(userWithFutureBirthDate))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Birth date is in the future");

        verify(messageComponent).getMessage(USER_BIRTH_DATE_IN_FUTURE);
        verify(loggerComponent).info(anyString(), any(Object[].class));
        verify(loggerComponent).error(anyString(), any(Object[].class));
    }

    @Test
    void when_allUsersAreQueried_then_returnListOfUsers() {

        // Arrange
        final var expectedUsers = Instancio.ofList(User.class).size(3).create();
        when(userProvider.findAll()).thenReturn(expectedUsers);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final List<User> actualUsers = userServiceImpl.findAll();

        // Assert
        assertThat(actualUsers).isEqualTo(expectedUsers);

        verify(userProvider).findAll();
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }

    @Test
    void when_filterByNameOrZipCode_then_returnMatchingUsers() {

        // Arrange
        final String query = "someNameOrZip";
        final var expectedUsers = Instancio.ofList(User.class).size(2).create();

        when(userProvider.filterByNameOrZipCode(query)).thenReturn(expectedUsers);
        doNothing().when(loggerComponent).info(anyString(), any(Object[].class));

        // Act
        final List<User> actualUsers = userServiceImpl.filterByNameOrZipCode(query);

        // Assert
        assertThat(actualUsers).isEqualTo(expectedUsers);

        verify(userProvider).filterByNameOrZipCode(query);
        verify(loggerComponent).info(anyString(), any(Object[].class));
    }
}
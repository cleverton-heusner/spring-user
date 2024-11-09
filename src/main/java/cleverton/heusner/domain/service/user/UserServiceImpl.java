package cleverton.heusner.domain.service.user;

import cleverton.heusner.domain.exception.BusinessException;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import cleverton.heusner.domain.model.User;
import cleverton.heusner.port.input.component.LoginContextComponent;
import cleverton.heusner.port.input.service.user.UserService;
import cleverton.heusner.port.output.provider.address.AddressProvider;
import cleverton.heusner.port.output.provider.user.UserProvider;
import cleverton.heusner.port.shared.LoggerComponent;
import cleverton.heusner.port.shared.MessageComponent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static cleverton.heusner.adapter.input.constant.business.UserBusinessErrorMessage.*;

public class UserServiceImpl implements UserService {

    private final UserProvider userProvider;
    private final AddressProvider addressProvider;
    private final MessageComponent messageComponent;
    private final LoggerComponent logger;
    private final LoginContextComponent loginContext;

    public UserServiceImpl(final UserProvider userProvider,
                           final AddressProvider addressProvider,
                           final MessageComponent messageComponent,
                           final LoggerComponent logger,
                           final LoginContextComponent loginContext) {
        this.userProvider = userProvider;
        this.addressProvider = addressProvider;
        this.messageComponent = messageComponent;
        this.logger = logger;
        this.loginContext = loginContext;
    }

    @Override
    public List<User> filterByNameOrZipCode(final String nameOrZipCode) {
        logger.info("Filtering users by name/ZIP code '%'.", nameOrZipCode);
        return userProvider.filterByNameOrZipCode(nameOrZipCode);
    }

    @Override
    public User findById(final String id) {
        logger.info("Retrieving user by ID '%'.", id);

        return userProvider.findById(Long.valueOf(id))
                .orElseThrow(() -> {
                    logger.error("Cannot retrieve user with ID '%'. Reason: user not found.", id);
                    throwUserNotFoundException(id, USER_NOT_FOUND_BY_ID_MESSAGE);
                    return null;
                });
    }

    @Override
    public User findByCpf(final String cpf) {
        logger.info("Retrieving user by CPF '%'.", cpf);

        return userProvider.findByCpf(cpf)
                .orElseThrow(() -> {
                    logger.error("Cannot retrieve user with CPF '%'. Reason: user not found.", cpf);
                    throwUserNotFoundException(cpf, USER_NOT_FOUND_BY_CPF_MESSAGE);
                    return null;
                });
    }

    private void throwUserNotFoundException(final String parameter, final String message) {
        throw new ResourceNotFoundException(messageComponent.getMessage(message, parameter));
    }

    @Override
    public User register(final User user) {
        logger.info("Registering user with name '%' and CPF '%'.", user.getName(), user.getCpf());

        validateUserBirthdate(user);
        validateUserDuplicityForRegister(user);

        user.setActive(true);
        user.getUserAuditingData().setRegisterUser(loginContext.getUserName());
        user.getUserAuditingData().setRegisterDateTime(LocalDateTime.now());
        user.setAddress(addressProvider.getAddressByZipCode(user.getAddress()));

        return userProvider.save(user);
    }

    private void validateUserDuplicityForRegister(final User user) {
        final var foundUserOptional = userProvider.findByCpf(user.getCpf());
        if (foundUserOptional.isPresent()) {
            logAndThrowUserExistingException(user.getName(), user.getCpf());
        }
    }

    @Override
    public User updateById(final Long userId, final User newUser) {
        logger.info("Updating user with name '%' and CPF '%'.", newUser.getName(), newUser.getCpf());

        validateUserBirthdate(newUser);
        final Optional<User> oldUserOptional = userProvider.findById(userId);

        if (oldUserOptional.isPresent()) {
            final User oldUser = oldUserOptional.get();
            validateUserDuplicityForUpdate(newUser, oldUser.getCpf());

            oldUser.setName(newUser.getName());
            oldUser.setBirthDate(newUser.getBirthDate());
            oldUser.setAddress(addressProvider.getAddressByZipCode(newUser.getAddress()));
            oldUser.getUserAuditingData().setLastUpdateUser(loginContext.getUserName());
            oldUser.getUserAuditingData().setLastUpdateDateTime(LocalDateTime.now());

            return userProvider.save(oldUser);
        }
        else {
            logger.error("Cannot retrieve user with ID '%'. Reason: user not found.", userId);
            throwUserNotFoundException(String.valueOf(userId), USER_NOT_FOUND_BY_ID_MESSAGE);
        }

        return newUser;
    }

    private void validateUserBirthdate(final User user) {
        if (user.isBirthDateInFuture()) {
            logger.error(
                    "Cannot register user with name '%' and CPF '%'. Reason: birthdate cannot be in the " +
                            "future.",
                    user.getName(),
                    user.getCpf()
            );

            throwBusinessException(USER_BIRTH_DATE_IN_FUTURE);
        }
    }

    private void validateUserDuplicityForUpdate(final User user, final String oldUserCpf) {
        final Optional<User> foundUserOptional = userProvider.findByCpf(user.getCpf());

        if (foundUserOptional.isPresent() && !oldUserCpf.equals(foundUserOptional.get().getCpf())) {
            logAndThrowUserExistingException(user.getName(), user.getCpf());
        }
    }

    private void logAndThrowUserExistingException(final String userName, final String userCpf) {
        logger.error(
                "Cannot register user with name '%' and CPF '%'. Reason: user already existing.",
                userName, userCpf
        );
        throw new ExistingResourceException(messageComponent.getMessage(USER_EXISTING_MESSAGE, userCpf));
    }

    @Override
    public User activateOrDeactivateById(final String id, final boolean activation) {
        final String operationInProgress = activation ? "Activating" : "Deactivating";
        logger.info("% user by ID '%'.", operationInProgress, id);

        final User user = findById(id);
        validateUserActivation(activation, user);
        user.setActive(activation);
        setActivationOrDeactivationUserAndDateTime(activation, user);

        return userProvider.save(user);
    }

    private void setActivationOrDeactivationUserAndDateTime(final boolean activation, final User user) {
        final var now = LocalDateTime.now();
        if (activation) {
            user.getUserAuditingData().setActivationUser(loginContext.getUserName());
            user.getUserAuditingData().setActivationDateTime(now);
        }
        else {
            user.getUserAuditingData().setDeactivationUser(loginContext.getUserName());
            user.getUserAuditingData().setDeactivationDateTime(now);
        }
    }

    private void validateUserActivation(final boolean activation, final User user) {
        if (activation == user.isActive()) {
            final String failedOperation = activation ? "activate" : "deactivate";
            final String state = activation ? "active" : "inactive";
            logger.error(
                    "Cannot % user with ID '%'. Reason: user already %.",
                    failedOperation,
                    user.getId(),
                    state
            );

            final String activationExceptionMessage = activation ? ALREADY_ACTIVE_USER : ALREADY_INACTIVE_USER;
            throwBusinessException(activationExceptionMessage, user.getId());
        }
    }

    private void throwBusinessException(final String message, final Object... params) {
        throw new BusinessException(messageComponent.getMessage(message, params));
    }

    @Override
    public List<User> findAll() {
        logger.info("Listing all registered users.");
        return userProvider.findAll();
    }
}
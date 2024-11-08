package cleverton.heusner.adapter.input.controller;

import cleverton.heusner.adapter.input.mapper.UserRegisterRequestMapper;
import cleverton.heusner.adapter.input.mapper.UserResponseMapper;
import cleverton.heusner.adapter.input.request.address.AddressRequest;
import cleverton.heusner.adapter.input.request.user.UserRegisterRequest;
import cleverton.heusner.adapter.input.response.AddressResponse;
import cleverton.heusner.adapter.input.response.UserResponse;
import cleverton.heusner.domain.model.Address;
import cleverton.heusner.domain.model.User;
import cleverton.heusner.port.input.service.user.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    private static final boolean IS_COMPLETE_ADDRESS = true;
    private static final boolean IS_INCOMPLETE_ADDRESS = false;

    @Mock
    protected UserRegisterRequestMapper userRegisterRequestMapper;

    @Mock
    protected UserResponseMapper userResponseMapper;

    @Mock
    protected UserService userService;

    @InjectMocks
    protected UserController userController;

    @BeforeEach
    protected void setUp() {
        RestAssuredMockMvc.standaloneSetup(userController);
    }

    protected User createUserWithCompleteAddress() {
        return createUser(IS_COMPLETE_ADDRESS);
    }

    protected User createUserWithIncompleteAddress() {
        return createUser(IS_INCOMPLETE_ADDRESS);
    }

    private User createUser(final boolean isCompleteAddress) {
        final long userId = 1;
        final String cpfUser = "33306418005";
        final String addressZipCode = "51130510";
        final String addressNumber = "12345";
        return Instancio.of(User.class)
                .set(Select.field(User::getId), userId)
                .set(Select.field(User::getCpf), cpfUser)
                .set(Select.field(User::isActive), true)
                .set(Select.field(User::getBirthDate), LocalDate.now())
                .set(Select.field(Address::isComplete), isCompleteAddress)
                .set(Select.field(Address::getZipCode), addressZipCode)
                .set(Select.field(Address::getNumber), addressNumber)
                .create();
    }

    protected UserResponse createUserResponse(final User expectedUser) {
        final var addressResponse = new AddressResponse(
                expectedUser.getAddress().getZipCode(),
                expectedUser.getAddress().getState(),
                expectedUser.getAddress().getState(),
                expectedUser.getAddress().getNeighborhood(),
                expectedUser.getAddress().getStreet(),
                expectedUser.getAddress().getComplement(),
                expectedUser.getAddress().getNumber());

        return new UserResponse(expectedUser.getId(),
                expectedUser.getName(),
                expectedUser.getCpf(),
                expectedUser.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                expectedUser.isActive(),
                expectedUser.getUserAuditingData(),
                addressResponse);
    }

    protected UserRegisterRequest createUserCreationRequest(final User expectedUser) {
        final var addressRequest = new AddressRequest(
                expectedUser.getAddress().getZipCode(),
                expectedUser.getAddress().getComplement(),
                expectedUser.getAddress().getNumber()
        );

        final var userCreationRequest = new UserRegisterRequest();
        userCreationRequest.setName(expectedUser.getName());
        userCreationRequest.setCpf(expectedUser.getCpf());
        userCreationRequest.setBirthDate(expectedUser.getBirthDate());
        userCreationRequest.setAddressRequest(addressRequest);

        return userCreationRequest;
    }
}
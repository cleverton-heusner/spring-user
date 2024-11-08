package cleverton.heusner.adapter.input.controller;

import cleverton.heusner.adapter.input.request.user.UserActivationRequest;
import cleverton.heusner.adapter.input.request.user.UserRegisterRequest;
import cleverton.heusner.adapter.input.response.UserResponse;
import cleverton.heusner.domain.model.User;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class UserControllerTest extends ControllerTest {

    @Test
    public void given_userExists_when_userIsQueriedById_then_okResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final String expectedUserId = String.valueOf(expectedUser.getId());

        when(userService.findById(expectedUserId)).thenReturn(expectedUser);
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/users/id/{id}", expectedUserId);
        final var actualUserResponse = response.as(UserResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUserResponse).isEqualTo(expectedUserResponse);

        verify(userService).findById(expectedUserId);
        verify(userResponseMapper).toResponse(expectedUser);
    }

    @Test
    public void given_userExists_when_userIsQueriedByCpf_then_okResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);

        when(userService.findByCpf(expectedUser.getCpf())).thenReturn(expectedUser);
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/users/cpf/{cpf}", expectedUser.getCpf());
        final var actualUserResponse = response.as(UserResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUserResponse).isEqualTo(expectedUserResponse);

        verify(userService).findByCpf(expectedUser.getCpf());
        verify(userResponseMapper).toResponse(expectedUser);
    }

    @Test
    public void given_userIsNew_and_hasCompleteAddress_when_userIsCreated_then_createdResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final UserRegisterRequest userRegisterRequest = createUserCreationRequest(expectedUser);

        when(userRegisterRequestMapper.toModel(userRegisterRequest)).thenReturn(expectedUser);
        when(userService.register(expectedUser)).thenReturn(expectedUser);
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(userRegisterRequest)
                .when()
                .post("/users");
        final var actualUserResponse = response.as(UserResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUserResponse).isEqualTo(expectedUserResponse);

        verify(userRegisterRequestMapper).toModel(userRegisterRequest);
        verify(userService).register(expectedUser);
        verify(userResponseMapper).toResponse(expectedUser);
    }

    @Test
    public void given_userIsNew_and_hasIncompleteAddress_when_userIsCreated_then_partialContentResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithIncompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final UserRegisterRequest userRegisterRequest = createUserCreationRequest(expectedUser);

        when(userRegisterRequestMapper.toModel(userRegisterRequest)).thenReturn(expectedUser);
        when(userService.register(expectedUser)).thenReturn(expectedUser);
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(userRegisterRequest)
                .when()
                .post("/users");
        final var actualUserResponse = response.as(UserResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(PARTIAL_CONTENT.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUserResponse).isEqualTo(expectedUserResponse);

        verify(userRegisterRequestMapper).toModel(userRegisterRequest);
        verify(userService).register(expectedUser);
        verify(userResponseMapper).toResponse(expectedUser);
    }

    @Test
    public void given_userExists_when_userIsActivated_then_okResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final var userActivationRequest = new UserActivationRequest(expectedUser.isActive());
        final String userId = String.valueOf(expectedUser.getId());

        when(userService.activateOrDeactivateById(userId, true)).thenReturn(expectedUser);
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(userActivationRequest)
                .when()
                .patch("/users/activation/id/{id}", userId);
        final var actualUserResponse = response.as(UserResponse.class);

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUserResponse).isEqualTo(expectedUserResponse);

        verify(userService).activateOrDeactivateById(userId, true);
        verify(userResponseMapper).toResponse(any(User.class));
    }

    @Test
    public void given_usersExist_when_usersAreFilteredByCpf_then_okResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final List<UserResponse> expectedUsersResponse = List.of(expectedUserResponse);

        when(userService.filterByNameOrZipCode(expectedUser.getCpf())).thenReturn(List.of(expectedUser));
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/users/name-or-zipcode/{nameOrZipCode}", expectedUser.getCpf());
        final List<UserResponse> actualUsers = response.as(new TypeRef<>(){});

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUsers).isEqualTo(expectedUsersResponse);

        verify(userService).filterByNameOrZipCode(expectedUser.getCpf());
        verify(userResponseMapper).toResponse(expectedUser);
    }

    @Test
    public void given_usersExist_when_usersAreQueried_then_okResponseIsReturned() {

        // Arrange
        final User expectedUser = createUserWithCompleteAddress();
        final UserResponse expectedUserResponse = createUserResponse(expectedUser);
        final List<UserResponse> expectedUsersResponse = List.of(expectedUserResponse);

        when(userService.findAll()).thenReturn(List.of(expectedUser));
        when(userResponseMapper.toResponse(expectedUser)).thenReturn(expectedUserResponse);

        // Act
        final var response = given()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get("/users");
        final List<UserResponse> actualUsers = response.as(new TypeRef<>(){});

        // Assert
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.contentType()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(actualUsers).isEqualTo(expectedUsersResponse);

        verify(userService).findAll();
        verify(userResponseMapper).toResponse(expectedUser);
    }
}
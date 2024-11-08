package cleverton.heusner.adapter.input.controller;

import cleverton.heusner.adapter.input.mapper.UserRegisterRequestMapper;
import cleverton.heusner.adapter.input.mapper.UserResponseMapper;
import cleverton.heusner.adapter.input.mapper.UserUpdateRequestMapper;
import cleverton.heusner.adapter.input.request.user.UserActivationRequest;
import cleverton.heusner.adapter.input.request.user.UserRegisterRequest;
import cleverton.heusner.adapter.input.request.user.UserUpdateRequest;
import cleverton.heusner.adapter.input.response.UserResponse;
import cleverton.heusner.adapter.input.validation.id.Id;
import cleverton.heusner.domain.model.Address;
import cleverton.heusner.domain.model.User;
import cleverton.heusner.port.input.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.HttpStatusCode.*;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.API_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.api.UserApiDoc.*;
import static cleverton.heusner.adapter.input.constant.validation.UserValidationErrorMessage.INVALID_USER_CPF;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Tag(
        name = USER_TAG_NAME,
        description = USER_TAG_DESCRIPTION
)
@RestController
@RequestMapping("users")
@PropertySource(value = API_MESSAGES + FILE_FORMAT, encoding = ENCODING, ignoreResourceNotFound = true)
public class UserController {

    private final UserRegisterRequestMapper userRegisterRequestMapper;
    private final UserUpdateRequestMapper userUpdateRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserService userService;

    public UserController(final UserRegisterRequestMapper userRegisterRequestMapper,
                          final UserUpdateRequestMapper userUpdateRequestMapper,
                          final UserResponseMapper userResponseMapper,
                          final UserService userService) {
        this.userRegisterRequestMapper = userRegisterRequestMapper;
        this.userUpdateRequestMapper = userUpdateRequestMapper;
        this.userResponseMapper = userResponseMapper;
        this.userService = userService;
    }

    @Operation(
            summary = USER_OPERATION_FIND_BY_ID_SUMMARY,
            description = USER_OPERATION_FIND_BY_ID_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_FIND_BY_ID_OK_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = USER_RESPONSE_FIND_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = USER_RESPONSE_FIND_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @GetMapping("id/{id}")
    public ResponseEntity<UserResponse> findById(@Valid
                                                     @PathVariable
                                                     @Parameter(required = true)
                                                     @Id
                                                     final String id) {
        final UserResponse userResponse = userResponseMapper.toResponse(userService.findById(id));
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = USER_OPERATION_FIND_BY_CPF_SUMMARY,
            description = USER_OPERATION_FIND_BY_CPF_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_FIND_BY_CPF_OK_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = USER_RESPONSE_FIND_BY_CPF_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = USER_RESPONSE_FIND_BY_CPF_BAD_REQUEST_DESCRIPTION_CPF,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @GetMapping("cpf/{cpf}")
    public ResponseEntity<UserResponse> findByCpf(@Valid
                                                      @PathVariable
                                                      @Parameter(required = true)
                                                      @CPF(message = INVALID_USER_CPF)
                                                      final String cpf) {
        final UserResponse userResponse = userResponseMapper.toResponse(userService.findByCpf(cpf));
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = USER_OPERATION_CREATE_SUMMARY,
            description = USER_OPERATION_CREATE_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = USER_OPERATION_CREATED_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = PARTIAL_CONTENT,
                    description = USER_OPERATION_PARTIAL_CONTENT_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = USER_RESPONSE_CREATE_BAD_REQUEST_DESCRIPTION_NAME,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = CONFLICT,
                    description = USER_RESPONSE_CREATE_CONFLICT_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid final UserRegisterRequest userRegisterRequest) {
        final User user = userRegisterRequestMapper.toModel(userRegisterRequest);
        final User savedUser = userService.register(user);

        return ResponseEntity.status(inferHttpStatus(savedUser.getAddress()))
                .body(userResponseMapper.toResponse(savedUser));
    }

    private HttpStatus inferHttpStatus(final Address address) {
        return address.isComplete() ? HttpStatus.CREATED : HttpStatus.PARTIAL_CONTENT;
    }

    @Operation(
            summary = USER_OPERATION_UPDATE_BY_ID_SUMMARY,
            description = USER_OPERATION_UPDATE_BY_ID_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_UPDATE_BY_ID_OK_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = USER_RESPONSE_UPDATE_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = USER_RESPONSE_UPDATE_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @PatchMapping("id/{id}")
    public ResponseEntity<UserResponse> updateById(@Valid
                                                       @PathVariable
                                                       @Parameter(required = true)
                                                       @Id
                                                       final String id,

                                                   @RequestBody
                                                   @Valid
                                                   final UserUpdateRequest userRegisterRequest) {

        final User user = userUpdateRequestMapper.toModel(userRegisterRequest);
        return ResponseEntity.ok(userResponseMapper.toResponse(userService.updateById(Long.parseLong(id), user)));
    }

    @Operation(
            summary = USER_OPERATION_ACTIVATE_BY_ID_SUMMARY,
            description = USER_OPERATION_ACTIVATE_BY_ID_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_ACTIVATE_BY_ID_OK_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = USER_RESPONSE_ACTIVATE_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = USER_RESPONSE_ACTIVATE_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @PatchMapping("activation/id/{id}")
    public ResponseEntity<UserResponse> activateOrDeactivate(@Valid
                                                                 @PathVariable
                                                                 @Parameter(required = true)
                                                                 @Id
                                                                 final String id,

                                                             @RequestBody
                                                             @Valid
                                                             final UserActivationRequest userActivationRequest) {

        final User user = userService.activateOrDeactivateById(id, userActivationRequest.active());
        return ResponseEntity.ok(userResponseMapper.toResponse(user));
    }

    @Operation(
            summary = USER_OPERATION_FILTER_BY_CPF_OR_ZIPCODE_SUMMARY,
            description = USER_OPERATION_FILTER_BY_CPF_OR_ZIPCODE_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_FILTER_BY_CPF_OR_ZIPCODE_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                    )
                    })
    })

    @GetMapping("name-or-zipcode/{nameOrZipCode}")
    public ResponseEntity<List<UserResponse>> filterByNameOrZipCode(@PathVariable
                                                                        @Parameter(required = true)
                                                                        final String nameOrZipCode) {
        final List<UserResponse> filteredUsers = userService.filterByNameOrZipCode(nameOrZipCode)
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(filteredUsers);
    }

    @Operation(
            summary = USER_OPERATION_FIND_ALL_SUMMARY,
            description = USER_OPERATION_FIND_ALL_DESCRIPTION,
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = USER_RESPONSE_FIND_ALL_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        final List<UserResponse> allUsers = userService.findAll()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(allUsers);
    }
}
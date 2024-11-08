package cleverton.heusner.adapter.input.controller;

import cleverton.heusner.adapter.input.request.login.LoginAuthenticationRequest;
import cleverton.heusner.adapter.input.request.login.LoginRegisterRequest;
import cleverton.heusner.adapter.input.request.login.TokenResponse;
import cleverton.heusner.adapter.output.configuration.security.TokenService;
import cleverton.heusner.adapter.output.entity.LoginEntity;
import cleverton.heusner.adapter.output.repository.LoginRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.HttpStatusCode.CREATED;
import static cleverton.heusner.adapter.input.constant.HttpStatusCode.OK;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.API_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.api.LoginApiDoc.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Tag(
        name = LOGIN_TAG_NAME,
        description = LOGIN_TAG_DESCRIPTION
)
@PropertySource(value = API_MESSAGES + FILE_FORMAT, encoding = ENCODING, ignoreResourceNotFound = true)
@RestController
@RequestMapping("login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final TokenService tokenService;

    public LoginController(final AuthenticationManager authenticationManager,
                           final LoginRepository loginRepository,
                           final TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.loginRepository = loginRepository;
        this.tokenService = tokenService;
    }

    @Operation(
            summary = LOGIN_OPERATION_AUTHENTICATION_SUMMARY,
            description = LOGIN_OPERATION_AUTHENTICATION_DESCRIPTION
    )
    @ApiResponse(
            responseCode = OK,
            description = LOGIN_OPERATION_AUTHENTICATED_DESCRIPTION,
            content = {@Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TokenResponse.class)
            )}
    )
    @PostMapping("authentication")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginAuthenticationRequest loginAuthenticationRequest) {
        final var usernamePassword  = new UsernamePasswordAuthenticationToken(loginAuthenticationRequest.username(), loginAuthenticationRequest.password());
        final Authentication authentication = authenticationManager.authenticate(usernamePassword);
        final String token = tokenService.generateToken((LoginEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Operation(
            summary = LOGIN_OPERATION_REGISTER_SUMMARY,
            description = LOGIN_OPERATION_REGISTER_DESCRIPTION
    )
    @ApiResponse(
            responseCode = CREATED,
            description = LOGIN_OPERATION_REGISTERED_DESCRIPTION
    )
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody @Valid LoginRegisterRequest loginRequest) {
        if (loginRepository.findByUsername(loginRequest.username()) != null) {
            return ResponseEntity.badRequest().build() ;
        }

        final var loginEntity = new LoginEntity(
                loginRequest.username(),
                encryptPassword(loginRequest.password()),
                loginRequest.role()
        );
        loginRepository.save(loginEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String encryptPassword(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Operation(
            summary = LOGIN_OPERATION_FIND_ALL_SUMMARY,
            description = LOGIN_OPERATION_FIND_ALL_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = LOGIN_RESPONSE_FIND_ALL_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LoginEntity.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<LoginEntity>> findAll() {
        return ResponseEntity.ok(loginRepository.findAll());
    }
}
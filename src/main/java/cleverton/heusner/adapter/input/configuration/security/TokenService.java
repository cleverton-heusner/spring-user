package cleverton.heusner.adapter.input.configuration.security;

import cleverton.heusner.adapter.output.entity.login.LoginEntity;
import cleverton.heusner.port.shared.LoggerComponent;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String tokenSecret;
    private final String tokenExpirationDateOffset;
    private final String tokenIssuer;
    private final LoggerComponent loggerComponent;

    public TokenService(@Value("${security.token.secret}") final String tokenSecret,
                        @Value("${security.token.expiration-date-offset}") final String tokenExpirationDateOffset,
                        @Value("${security.token.issuer}") final String tokenIssuer,
                        final LoggerComponent loggerComponent) {
        this.tokenSecret = tokenSecret;
        this.tokenExpirationDateOffset = tokenExpirationDateOffset;
        this.tokenIssuer = tokenIssuer;
        this.loggerComponent = loggerComponent;
    }

    public String generateToken(final LoginEntity login) {
        loggerComponent.info("Generating token for login with username %.", login.getUsername());

        try {
            return JWT.create()
                    .withIssuer(tokenIssuer)
                    .withSubject(login.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(Algorithm.HMAC256(tokenSecret));
        }
        catch (final JWTCreationException e) {
            throw new RuntimeException("Cannot generate token. Reason: ", e);
        }
    }

    public String validateToken(final String token) {
        try {
            return JWT.require(Algorithm.HMAC256(tokenSecret))
                    .withIssuer(tokenIssuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch(final JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(tokenExpirationDateOffset));
    }
}
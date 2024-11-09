package cleverton.heusner.adapter.input.configuration.security;

import cleverton.heusner.port.input.service.login.LoginService;
import cleverton.heusner.port.shared.LoggerComponent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final LoginService loginService;
    private final LoggerComponent logger;

    public TokenFilter(final TokenService tokenService,
                       final LoginService loginService,
                       final LoggerComponent logger) {
        this.tokenService = tokenService;
        this.loginService = loginService;
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        authenticateIfTokenIsValid(request);
        filterChain.doFilter(request, response);
    }

    private void authenticateIfTokenIsValid(final HttpServletRequest request) {
        recoverToken(request).ifPresent(token -> {
            final String username = tokenService.validateToken(token);
            final UserDetails login = loginService.loadUserByUsername(username);
            final var authentication = new UsernamePasswordAuthenticationToken(
                    login,
                    null,
                    login.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Login authenticated.");
        });
    }

    private Optional<String> recoverToken(final HttpServletRequest request) {
        logger.info("Recovering token.");

        final String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader == null ?
                Optional.empty() :
                Optional.of(authorizationHeader.replace("Bearer ", ""));
    }
}
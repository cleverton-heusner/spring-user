package cleverton.heusner.adapter.input.exception;

import cleverton.heusner.domain.exception.BusinessException;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void test_handleResourceNotFoundException() {

        // Arrange
        final var exception = new ResourceNotFoundException("Resource not found");
        when(request.getServletPath()).thenReturn("/test-path");

        // Act
        final ResponseEntity<Object> response = globalExceptionHandler.handleResourceNotFoundException(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", ((ProblemDetail) response.getBody()).getDetail());
    }

    @Test
    void test_handleExistingResourceException() {

        // Arrange
        final var exception = new ExistingResourceException("Resource already exists");
        when(request.getServletPath()).thenReturn("/test-path");

        // Act
        final ResponseEntity<Object> response = globalExceptionHandler.handleExistingResourceException(exception, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Resource already exists", ((ProblemDetail) response.getBody()).getDetail());
    }

    @Test
    void test_handleConstraintViolationExceptions() {

        // Arrange
        final var exception = new ConstraintViolationException("Constraint violated", Collections.emptySet());
        when(request.getServletPath()).thenReturn("/test-path");

        // Act
        final ResponseEntity<Object> response = globalExceptionHandler.handleConstraintViolationExceptions(exception, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Constraint violated", ((ProblemDetail) response.getBody()).getDetail());
    }

    @Test
    void test_handleBusinessException() {

        // Arrange
        final var exception = new BusinessException("Business rule violated");
        when(request.getServletPath()).thenReturn("/test-path");

        // Act
        final ResponseEntity<Object> response = globalExceptionHandler.handleBusinessException(exception, request);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Business rule violated", ((ProblemDetail) response.getBody()).getDetail());
    }
}
package cleverton.heusner.domain.exception;

public class UnavailableResourceException extends RuntimeException {

    public UnavailableResourceException(final String message) {
        super(message);
    }
}
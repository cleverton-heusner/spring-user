package cleverton.heusner.shared.utils;

import cleverton.heusner.port.shared.LoggerComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerComponentImpl implements LoggerComponent {

    private static final Logger logger = LoggerFactory.getLogger(LoggerComponentImpl.class);

    @Override
    public void info(final String message, final Object... params) {
        logger.info(formatMessage(message), params);
    }

    @Override
    public void warn(final String message, final Object... params) {
        logger.warn(formatMessage(message), params);
    }

    @Override
    public void error(final String message, final Object... params) {
        logger.error(formatMessage(message), params);
    }

    private String formatMessage(final String message) {
        return message.replace("%", "{}");
    }
}
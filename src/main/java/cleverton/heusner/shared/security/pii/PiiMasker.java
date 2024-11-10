package cleverton.heusner.shared.security.pii;

import com.fasterxml.jackson.core.JsonStreamContext;
import net.logstash.logback.mask.ValueMasker;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PiiMasker implements ValueMasker {

    private static final String MESSAGE = "message";
    private final Map<Pattern, String> patterns = new HashMap<>();

    public PiiMasker() {
        patterns.put(Pattern.compile("\\d{6}(\\d{5})"), "******$1");
    }

    @Override
    public Object mask(final JsonStreamContext context, final Object unmaskedMessage) {
        if (context.getCurrentName().equals(MESSAGE) && unmaskedMessage instanceof String message) {
            return patterns.keySet()
                    .stream()
                    .map(pattern -> pattern.matcher(message).replaceAll(patterns.get(pattern)))
                    .collect(Collectors.joining());
        }

        return unmaskedMessage;
    }
}
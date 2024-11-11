package cleverton.heusner.shared.security.pii;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.mask.ValueMasker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PiiMasker implements ValueMasker {

    private static final String MESSAGE = "message";
    private static final Map<Pattern, String> masksPatterns = new HashMap<>();

    public PiiMasker() {
        try (final var is = PiiMasker.class.getClassLoader().getResourceAsStream("pii_masks.json")) {
            final var objectMapper = new ObjectMapper();
            final List<Mask> masks = objectMapper.readValue(is, new TypeReference<>() {});
            masks.forEach(mask -> masksPatterns.put(Pattern.compile(mask.getPattern()), mask.getMask()));
        } catch (final IOException e) {
            throw new RuntimeException("Cannot read masks file. Reason: "  + e);
        }
    }

    @Override
    public Object mask(final JsonStreamContext context, final Object unmaskedMessage) {
        if (context.getCurrentName().equals(MESSAGE) && unmaskedMessage instanceof String message) {
            return masksPatterns.keySet()
                    .stream()
                    .map(pattern -> pattern.matcher(message).replaceAll(masksPatterns.get(pattern)))
                    .collect(Collectors.joining());
        }

        return unmaskedMessage;
    }
}
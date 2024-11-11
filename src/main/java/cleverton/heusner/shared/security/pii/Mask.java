package cleverton.heusner.shared.security.pii;

import lombok.Data;

@Data
public class Mask {

    private String name;
    private String pattern;
    private String mask;
}
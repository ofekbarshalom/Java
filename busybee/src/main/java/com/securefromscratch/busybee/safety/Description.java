package com.securefromscratch.busybee.safety;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import java.util.regex.Pattern;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Schema(type = "String", description = "Task description with allowed HTML")
public class Description {
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 2000;

    // OWASP HTML Sanitizer policy - allows specific safe tags
    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
        // Allow <a> tags with href but restrict protocols to http/https/mailto
        .allowElements("a")
        .allowAttributes("href").onElements("a")
        .allowStandardUrlProtocols()
        .allowUrlProtocols("mailto")
        .requireRelNofollowOnLinks()
        // Allow <img> tags with src and alt
        .allowElements("img")
        .allowAttributes("src", "alt").onElements("img")
        // Allow text formatting tags
        .allowElements("b", "i", "u", "strong", "em")
        // Allow line breaks
        .allowElements("br")
        // Allow paragraphs and divs
        .allowElements("p", "div")
        // Disallow everything else
        .toFactory();

    private final String value;

    @ConstructorBinding
    public Description(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Description length must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH)
            );
        }
        
        // Sanitize the HTML content
        String sanitized = POLICY.sanitize(value);
        this.value = sanitized;
    }

    @JsonValue
    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

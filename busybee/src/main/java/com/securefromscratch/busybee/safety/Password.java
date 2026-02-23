package com.securefromscratch.busybee.safety;

import io.swagger.v3.oas.annotations.media.Schema;
import org.owasp.safetypes.exception.TypeValidationException;
import org.owasp.safetypes.types.string.BoundedString;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Schema(type = "String", description = "Password")
public class Password extends BoundedString {
    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 100;

    private final String value;

    @ConstructorBinding
    public Password(String value) throws TypeValidationException {
        super(value);
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() { return value; }

    @Override
    public Integer min() { return MIN_LENGTH; }

    @Override
    public Integer max() { return MAX_LENGTH; }
}

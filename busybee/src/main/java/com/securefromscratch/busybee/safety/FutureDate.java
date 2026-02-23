package com.securefromscratch.busybee.safety;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Schema(type = "String", description = "Future or current date in ISO format (YYYY-MM-DD)")
public class FutureDate {
    private final LocalDate value;

    @ConstructorBinding
    public FutureDate(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }

        LocalDate parsed;
        try {
            parsed = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected ISO format (YYYY-MM-DD): " + e.getMessage());
        }

        LocalDate today = LocalDate.now();
        if (parsed.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past. Provided: " + parsed + ", Today: " + today);
        }

        this.value = parsed;
    }

    public LocalDate getDate() {
        return value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FutureDate that = (FutureDate) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

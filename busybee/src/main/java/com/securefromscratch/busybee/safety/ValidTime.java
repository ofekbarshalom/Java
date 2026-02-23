package com.securefromscratch.busybee.safety;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Schema(type = "String", description = "Valid time in ISO format (HH:MM:SS)")
public class ValidTime {
    private final LocalTime value;

    @ConstructorBinding
    public ValidTime(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            throw new IllegalArgumentException("Time cannot be null or empty");
        }

        LocalTime parsed;
        try {
            parsed = LocalTime.parse(timeString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Expected ISO format (HH:MM:SS): " + e.getMessage());
        }

        this.value = parsed;
    }

    public LocalTime getTime() {
        return value;
    }

    //Check if the given date+time combination is in the past.
    public boolean isPastWhen(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDateTime dateTime = LocalDateTime.of(date, value);
        return dateTime.isBefore(LocalDateTime.now());
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
        ValidTime that = (ValidTime) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

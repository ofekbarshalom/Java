package com.securefromscratch.busybee.safety;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class ValidTimeTest {

    @Test
    public void validTimeFormatIsAccepted() {
        ValidTime vt = new ValidTime("14:30:00");
        assertEquals(LocalTime.of(14, 30, 0), vt.getTime());
    }

    @Test
    public void validTimeShortFormatIsAccepted() {
        // LocalTime.parse accepts HH:MM format
        ValidTime vt = new ValidTime("14:30");
        assertEquals(LocalTime.of(14, 30), vt.getTime());
    }

    @Test
    public void futureTimeOnTodayIsNotPast() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalTime futureTime = now.plusHours(2);
        if (futureTime.isBefore(now)) {
            futureTime = now.plusSeconds(1);
            if (futureTime.isBefore(now)) {
                futureTime = now;
            }
        }
        ValidTime vt = new ValidTime(futureTime.toString());
        assertFalse(vt.isPastWhen(today), "Future time today should not be past");
    }

    @Test
    public void pastTimeOnTodayIsPast() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Assumptions.assumeFalse(now.equals(LocalTime.MIDNIGHT), "Cannot pick a past time at midnight");
        LocalTime pastTime = now.minusHours(2);
        if (pastTime.isAfter(now)) {
            pastTime = now.minusSeconds(1);
        }
        ValidTime vt = new ValidTime(pastTime.toString());
        assertTrue(vt.isPastWhen(today), "Past time today should be past");
    }

    @Test
    public void anyTimeOnFutureDateIsNotPast() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        ValidTime vt = new ValidTime("00:00:00");
        assertFalse(vt.isPastWhen(tomorrow), "Any time on future date should not be past");
    }

    @Test
    public void anyTimeOnPastDateIsPast() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        ValidTime vt = new ValidTime("23:59:59");
        assertTrue(vt.isPastWhen(yesterday), "Any time on past date should be past");
    }

    @Test
    public void isPastWhenNullDateReturnsFalse() {
        ValidTime vt = new ValidTime("14:30:00");
        assertFalse(vt.isPastWhen(null), "isPastWhen(null) should return false");
    }

    @Test
    public void invalidTimeFormatIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ValidTime("25:00:00")); // invalid hour
        assertThrows(IllegalArgumentException.class, () -> new ValidTime("14:60:00")); // invalid minute
        assertThrows(IllegalArgumentException.class, () -> new ValidTime("not-a-time"));
    }

    @Test
    public void nullTimeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ValidTime(null));
    }

    @Test
    public void emptyTimeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ValidTime(""));
        assertThrows(IllegalArgumentException.class, () -> new ValidTime("   "));
    }

    @Test
    public void toStringReturnsTimeInIsoFormat() {
        ValidTime vt = new ValidTime("14:30:00");
        String result = vt.toString();
        // LocalTime.toString() omits trailing zeros, so "14:30:00" becomes "14:30"
        assertTrue(result.startsWith("14:30"), "toString should return ISO format time: " + result);
    }
}

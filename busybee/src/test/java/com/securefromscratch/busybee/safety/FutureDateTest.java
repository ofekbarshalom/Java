package com.securefromscratch.busybee.safety;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class FutureDateTest {

    @Test
    public void todayIsValid() {
        String today = LocalDate.now().toString();
        FutureDate fd = new FutureDate(today);
        assertEquals(LocalDate.now(), fd.getDate());
    }

    @Test
    public void futureDateIsValid() {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        FutureDate fd = new FutureDate(tomorrow);
        assertEquals(LocalDate.now().plusDays(1), fd.getDate());
    }

    @Test
    public void pastDateIsRejected() {
        String yesterday = LocalDate.now().minusDays(1).toString();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, 
            () -> new FutureDate(yesterday));
        assertTrue(ex.getMessage().contains("cannot be in the past"), 
            "Error message should mention past date: " + ex.getMessage());
    }

    @Test
    public void invalidFormatIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FutureDate("not-a-date"));
        assertThrows(IllegalArgumentException.class, () -> new FutureDate("2026/02/20")); // wrong format
        assertThrows(IllegalArgumentException.class, () -> new FutureDate("20-02-2026")); // wrong format
    }

    @Test
    public void nullDateIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FutureDate(null));
    }

    @Test
    public void emptyDateIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FutureDate(""));
        assertThrows(IllegalArgumentException.class, () -> new FutureDate("   "));
    }

    @Test
    public void toStringReturnsIsoFormat() {
        String dateStr = "2026-12-25";
        FutureDate fd = new FutureDate(dateStr);
        assertEquals(dateStr, fd.toString());
    }
}

package ru.college.calendar;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CalendarEventTest {
    private final LocalDateTime base = LocalDateTime.of(2026, 1, 1, 10, 0);

    @Test void acceptsMinimumPositiveDuration() {
        CalendarEvent e = new CalendarEvent("Встреча", base, base.plusNanos(1));
        assertEquals("Встреча", e.getTitle());
    }
    @Test void acceptsExactlyThirtyDays() {
        assertDoesNotThrow(() -> new CalendarEvent("Отпуск", base, base.plusDays(30)));
    }
    @Test void rejectsMoreThanThirtyDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new CalendarEvent("Отпуск", base, base.plusDays(30).plusNanos(1)));
    }
    @Test void rejectsEqualStartAndEnd() {
        assertThrows(IllegalArgumentException.class, () -> new CalendarEvent("X", base, base));
    }
    @Test void rejectsEndBeforeStart() {
        assertThrows(IllegalArgumentException.class, () -> new CalendarEvent("X", base, base.minusSeconds(1)));
    }
    @Test void rejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new CalendarEvent("  ", base, base.plusHours(1)));
    }
    @Test void rejectsNullDates() {
        assertThrows(IllegalArgumentException.class, () -> new CalendarEvent("X", null, base));
    }
    @Test void trimsTitleAndFormatsEvent() {
        CalendarEvent e = new CalendarEvent("  Планёрка  ", base, base.plusHours(1));
        assertEquals("Планёрка", e.getTitle());
        assertTrue(e.toString().contains("Планёрка"));
    }
    @Test void detectsOverlapAndAdjacentEvents() {
        CalendarEvent first = new CalendarEvent("A", base, base.plusHours(1));
        CalendarEvent overlap = new CalendarEvent("B", base.plusMinutes(59), base.plusHours(2));
        CalendarEvent adjacent = new CalendarEvent("C", base.plusHours(1), base.plusHours(2));
        assertTrue(first.overlaps(overlap));
        assertFalse(first.overlaps(adjacent));
    }
}
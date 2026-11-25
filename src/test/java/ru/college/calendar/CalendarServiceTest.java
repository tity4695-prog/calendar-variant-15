package ru.college.calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceTest {
    private CalendarService calendar;
    private LocalDateTime start;
    @BeforeEach void setUp() {
        calendar = new CalendarService();
        start = LocalDateTime.of(2026, 5, 10, 9, 0);
    }
    private CalendarEvent event(String title, int hour) {
        LocalDateTime s = start.withHour(hour);
        return new CalendarEvent(title, s, s.plusHours(1));
    }
    @Test void addsAndCountsEvents() {
        calendar.addEvent(event("Лекция", 9));
        assertEquals(1, calendar.count());
    }
    @Test void rejectsNullEvent() {
        assertThrows(IllegalArgumentException.class, () -> calendar.addEvent(null));
    }
    @Test void rejectsOverlappingEvents() {
        calendar.addEvent(event("Лекция", 9));
        assertThrows(IllegalStateException.class, () -> calendar.addEvent(event("Встреча", 9)));
    }
    @Test void allowsAdjacentEventsAndSortsThem() {
        calendar.addEvent(event("Позже", 11));
        calendar.addEvent(event("Раньше", 9));
        assertEquals("Раньше", calendar.getEvents().get(0).getTitle());
    }
    @Test void findsIgnoringCase() {
        calendar.addEvent(event("Лекция", 9));
        assertTrue(calendar.findByTitle("лекция").isPresent());
    }
    @Test void returnsEmptyForMissingOrBlankTitle() {
        assertTrue(calendar.findByTitle("нет").isEmpty());
        assertTrue(calendar.findByTitle(" ").isEmpty());
    }
    @Test void removesEventAndReturnsStatus() {
        calendar.addEvent(event("Лекция", 9));
        assertTrue(calendar.removeEvent("лекция"));
        assertFalse(calendar.removeEvent("лекция"));
        assertEquals(0, calendar.count());
    }
    @Test void returnsImmutableEventList() {
        assertThrows(UnsupportedOperationException.class, () -> calendar.getEvents().add(event("X", 9)));
    }
}
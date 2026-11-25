package ru.college.calendar;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CalendarIntegrationTest {
    @Test void createAddFindRemoveFlow() {
        CalendarService service = new CalendarService();
        LocalDateTime start = LocalDateTime.of(2026, 9, 1, 12, 0);
        CalendarEvent event = new CalendarEvent("Защита проекта", start, start.plusHours(2));
        service.addEvent(event);
        assertTrue(service.findByTitle("Защита проекта").isPresent());
        assertTrue(service.removeEvent("Защита проекта"));
        assertTrue(service.findByTitle("Защита проекта").isEmpty());
        assertEquals(0, service.count());
    }
    @Test void conflictIsRejectedWithoutCorruptingCalendar() {
        CalendarService service = new CalendarService();
        LocalDateTime start = LocalDateTime.of(2026, 9, 1, 12, 0);
        service.addEvent(new CalendarEvent("Первое", start, start.plusHours(2)));
        assertThrows(IllegalStateException.class, () ->
                service.addEvent(new CalendarEvent("Конфликт", start.plusHours(1), start.plusHours(3))));
        assertEquals(1, service.count());
    }
}
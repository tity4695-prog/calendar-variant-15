package ru.college.calendar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CalendarService {
    private final List<CalendarEvent> events = new ArrayList<>();

    public void addEvent(CalendarEvent event) {
        if (event == null) throw new IllegalArgumentException("Событие не должно быть null");
        if (events.stream().anyMatch(event::overlaps))
            throw new IllegalStateException("Время события пересекается с существующим");
        events.add(event);
        events.sort(Comparator.comparing(CalendarEvent::getStart));
    }

    public boolean removeEvent(String title) {
        if (title == null || title.isBlank()) return false;
        return events.removeIf(e -> e.getTitle().equalsIgnoreCase(title.trim()));
    }

    public Optional<CalendarEvent> findByTitle(String title) {
        if (title == null || title.isBlank()) return Optional.empty();
        return events.stream().filter(e -> e.getTitle().equalsIgnoreCase(title.trim())).findFirst();
    }

    public List<CalendarEvent> getEvents() { return List.copyOf(events); }
    public int count() { return events.size(); }
}
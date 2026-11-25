package ru.college.calendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEvent {
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public CalendarEvent(String title, LocalDateTime start, LocalDateTime end) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Название обязательно");
        if (start == null || end == null) throw new IllegalArgumentException("Дата и время обязательны");
        if (!end.isAfter(start)) throw new IllegalArgumentException("Окончание должно быть позже начала");
        if (Duration.between(start, end).compareTo(Duration.ofDays(30)) > 0)
            throw new IllegalArgumentException("Событие не может длиться более 30 дней");
        this.title = title.trim();
        this.start = start;
        this.end = end;
    }

    public String getTitle() { return title; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }

    public boolean overlaps(CalendarEvent other) {
        Objects.requireNonNull(other, "Событие не должно быть null");
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    @Override public String toString() { return title + " [" + start + " — " + end + "]"; }
}
package com.starfireaviation.sweepline;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a time-based event with a start and end time.
 */
class TimeEvent implements Comparable<TimeEvent> {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String data;
    private final String id;

    public TimeEvent(LocalDateTime start, LocalDateTime end, String data) {
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.data = data;
        this.id = UUID.randomUUID().toString();
    }

    public TimeEvent(LocalDateTime start, LocalDateTime end, String data, String id) {
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.data = data;
        this.id = id;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public String getData() { return data; }
    public String getId() { return id; }

    public boolean overlaps(TimeEvent other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public int compareTo(TimeEvent other) {
        int cmp = this.start.compareTo(other.start);
        if (cmp != 0) return cmp;
        return this.end.compareTo(other.end);
    }

    @Override
    public String toString() {
        return String.format("Event[%s - %s: %s]", start, end, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeEvent)) return false;
        TimeEvent event = (TimeEvent) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}


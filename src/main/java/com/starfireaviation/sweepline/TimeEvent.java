package com.starfireaviation.sweepline;

import java.util.UUID;

/**
 * Represents a time-based event with a start and end time.
 */
class TimeEvent implements Comparable<TimeEvent> {
    private final long start;
    private final long end;
    private final String data;
    private final String id;

    public TimeEvent(long start, long end, String data) {
        if (start >= end) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.data = data;
        this.id = UUID.randomUUID().toString();
    }

    public TimeEvent(long start, long end, String data, String id) {
        if (start >= end) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.data = data;
        this.id = id;
    }

    public long getStart() { return start; }
    public long getEnd() { return end; }
    public String getData() { return data; }
    public String getId() { return id; }

    public boolean overlaps(TimeEvent other) {
        return this.start < other.end && other.start < this.end;
    }

    @Override
    public int compareTo(TimeEvent other) {
        int cmp = Long.compare(this.start, other.start);
        if (cmp != 0) return cmp;
        return Long.compare(this.end, other.end);
    }

    @Override
    public String toString() {
        return String.format("Event[%d-%d: %s]", start, end, data);
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


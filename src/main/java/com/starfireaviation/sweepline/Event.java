package com.starfireaviation.sweepline;

import java.time.LocalDateTime;

public class Event {

    private LocalDateTime start;

    private LocalDateTime end;

    private String title;

    public Event() {}

    public Event(final LocalDateTime start, final LocalDateTime end, final String title) {
        this.start = start;
        this.end = end;
        this.title = title;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(final LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(final LocalDateTime end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}

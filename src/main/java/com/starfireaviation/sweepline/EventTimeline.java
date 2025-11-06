package com.starfireaviation.sweepline;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Manages a timeline of events ensuring only one event is active at any point in time.
 * Uses a sweep line algorithm approach to handle event insertions and maintain
 * non-overlapping intervals.
 */
class EventTimeline {
    private final TreeSet<TimeEvent> events;

    public EventTimeline() {
        this.events = new TreeSet<>();
    }

    /**
     * Inserts a new event into the timeline, breaking apart any existing events
     * that overlap with the new event. The new event takes priority.
     *
     * @param newEvent the event to insert
     * @return list of events that were modified or removed
     */
    public List<TimeEvent> insert(TimeEvent newEvent) {
        List<TimeEvent> affectedEvents = new ArrayList<>();
        List<TimeEvent> eventsToAdd = new ArrayList<>();
        List<TimeEvent> eventsToRemove = new ArrayList<>();

        // Find all events that overlap with the new event
        for (TimeEvent existing : events) {
            if (existing.overlaps(newEvent)) {
                affectedEvents.add(existing);
                eventsToRemove.add(existing);

                // Create fragments of the existing event that don't overlap
                // Left fragment: if existing starts before new event
                if (existing.getStart() < newEvent.getStart()) {
                    TimeEvent leftFragment = new TimeEvent(
                            existing.getStart(),
                            newEvent.getStart(),
                            existing.getData()
                    );
                    eventsToAdd.add(leftFragment);
                }

                // Right fragment: if existing ends after new event
                if (existing.getEnd() > newEvent.getEnd()) {
                    TimeEvent rightFragment = new TimeEvent(
                            newEvent.getEnd(),
                            existing.getEnd(),
                            existing.getData()
                    );
                    eventsToAdd.add(rightFragment);
                }
            }
        }

        // Remove overlapping events
        events.removeAll(eventsToRemove);

        // Add the new event
        events.add(newEvent);

        // Add the fragments
        events.addAll(eventsToAdd);

        return affectedEvents;
    }

    /**
     * Gets all events in chronological order.
     *
     * @return sorted list of all events
     */
    public List<TimeEvent> getAllEvents() {
        return new ArrayList<>(events);
    }

    /**
     * Gets the active event at a specific time point.
     *
     * @param time the time to query
     * @return the active event at that time, or null if none
     */
    public TimeEvent getEventAt(long time) {
        for (TimeEvent event : events) {
            if (time >= event.getStart() && time < event.getEnd()) {
                return event;
            }
        }
        return null;
    }

    /**
     * Removes an event from the timeline.
     *
     * @param event the event to remove
     * @return true if the event was removed, false otherwise
     */
    public boolean remove(TimeEvent event) {
        return events.remove(event);
    }

    /**
     * Verifies that no events overlap (for validation/testing).
     *
     * @return true if timeline is valid (no overlaps), false otherwise
     */
    public boolean validate() {
        List<TimeEvent> eventList = getAllEvents();
        for (int i = 0; i < eventList.size() - 1; i++) {
            TimeEvent current = eventList.get(i);
            TimeEvent next = eventList.get(i + 1);
            if (current.overlaps(next)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets events within a specific time range.
     *
     * @param start start of the range
     * @param end end of the range
     * @return list of events that overlap with the range
     */
    public List<TimeEvent> getEventsInRange(long start, long end) {
        List<TimeEvent> result = new ArrayList<>();
        TimeEvent rangeEvent = new TimeEvent(start, end, "");

        for (TimeEvent event : events) {
            if (event.overlaps(rangeEvent)) {
                result.add(event);
            }
        }
        return result;
    }

    /**
     * Clears all events from the timeline.
     */
    public void clear() {
        events.clear();
    }

    /**
     * Returns the number of events in the timeline.
     */
    public int size() {
        return events.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EventTimeline:\n");
        for (TimeEvent event : events) {
            sb.append("  ").append(event).append("\n");
        }
        return sb.toString();
    }
}

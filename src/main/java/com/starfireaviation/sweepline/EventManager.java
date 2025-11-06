package com.starfireaviation.sweepline;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Demonstration and testing of the EventTimeline class.
 */
@Component
public class EventManager {

    @PostConstruct
    public static void test(String[] args) {
        EventTimeline timeline = new EventTimeline();

        System.out.println("=== Event Timeline Manager Demo ===\n");

        // Test 1: Add initial events
        System.out.println("Test 1: Adding initial events");
        timeline.insert(new TimeEvent(0, 10, "Event A"));
        timeline.insert(new TimeEvent(20, 30, "Event B"));
        timeline.insert(new TimeEvent(40, 50, "Event C"));
        System.out.println(timeline);
        System.out.println("Valid: " + timeline.validate() + "\n");

        // Test 2: Insert event that completely overlaps another
        System.out.println("Test 2: Insert event (15-25) that overlaps Event B (20-30)");
        List<TimeEvent> affected = timeline.insert(new TimeEvent(15, 25, "Event D"));
        System.out.println("Affected events: " + affected);
        System.out.println(timeline);
        System.out.println("Valid: " + timeline.validate() + "\n");

        // Test 3: Insert event that splits an existing event
        System.out.println("Test 3: Insert event (42-46) that splits Event C (40-50)");
        affected = timeline.insert(new TimeEvent(42, 46, "Event E"));
        System.out.println("Affected events: " + affected);
        System.out.println(timeline);
        System.out.println("Valid: " + timeline.validate() + "\n");

        // Test 4: Insert event that overlaps multiple events
        System.out.println("Test 4: Insert event (5-44) that spans multiple events");
        affected = timeline.insert(new TimeEvent(5, 44, "Event F"));
        System.out.println("Affected events: " + affected);
        System.out.println(timeline);
        System.out.println("Valid: " + timeline.validate() + "\n");

        // Test 5: Query active event at specific times
        System.out.println("Test 5: Query events at specific times");
        long[] testTimes = {3, 7, 12, 25, 45, 49, 55};
        for (long time : testTimes) {
            TimeEvent event = timeline.getEventAt(time);
            System.out.printf("  Time %d: %s%n", time,
                    event != null ? event.getData() : "No event");
        }
        System.out.println();

        // Test 6: Get events in a range
        System.out.println("Test 6: Get events in range [2-48]");
        List<TimeEvent> rangeEvents = timeline.getEventsInRange(2, 48);
        for (TimeEvent event : rangeEvents) {
            System.out.println("  " + event);
        }
        System.out.println();

        // Test 7: Complex scenario
        System.out.println("Test 7: Complex scenario with multiple insertions");
        timeline.clear();
        timeline.insert(new TimeEvent(0, 100, "Background"));
        System.out.println("After adding background event (0-100):");
        System.out.println(timeline);

        timeline.insert(new TimeEvent(10, 20, "Task 1"));
        timeline.insert(new TimeEvent(30, 40, "Task 2"));
        timeline.insert(new TimeEvent(50, 60, "Task 3"));
        System.out.println("After adding three tasks:");
        System.out.println(timeline);

        timeline.insert(new TimeEvent(15, 55, "Priority Task"));
        System.out.println("After inserting priority task (15-55):");
        System.out.println(timeline);
        System.out.println("Valid: " + timeline.validate());
    }
}
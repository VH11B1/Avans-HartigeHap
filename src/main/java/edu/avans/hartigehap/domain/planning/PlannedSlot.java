package edu.avans.hartigehap.domain.planning;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Alex on 3-3-2015.
 */
public class PlannedSlot extends TimeSlot {
    public PlannedSlot(TimeSlot.DayPart dayPart, LocalDateTime start, LocalDateTime end) {
        super(dayPart, start, end);
    }
}

package edu.avans.hartigehap.domain.planning;

import java.util.Date;

/**
 * Created by Alex on 3-3-2015.
 */
public class PlannedSlot extends TimeSlot {
    public PlannedSlot(TimeSlot.DayPart dayPart, Date start, Date end) {
        super(dayPart, start, end);
    }
}

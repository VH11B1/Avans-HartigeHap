package edu.avans.hartigehap.domain.planning;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
public class AvailableSlot extends TimeSlot {

    private boolean continuous;

    public AvailableSlot(DayPart dayPart, LocalDateTime start, LocalDateTime end,
                         boolean continuous) {
        super(dayPart, start, end);
        this.continuous = continuous;
    }
}

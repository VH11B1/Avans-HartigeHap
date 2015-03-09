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
public class ActualSlot extends TimeSlot {
    private Employee actualEmployee;

    public ActualSlot(TimeSlot.DayPart dayPart, LocalDateTime start, LocalDateTime end,
                      Employee actualEmployee) {
        super(dayPart, start, end);
        this.actualEmployee = actualEmployee;
    }
}

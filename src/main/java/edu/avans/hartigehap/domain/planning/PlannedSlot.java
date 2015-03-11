package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Alex on 3-3-2015.
 */
@Entity
@Table(name = "PLANNEDSLOTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class PlannedSlot extends TimeSlot {
    public PlannedSlot(TimeSlot.DayPart dayPart, LocalDateTime start, LocalDateTime end) {
        super(dayPart, start, end);
    }
}

package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
@Entity
@Table(name = "AVAILABLESLOTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class AvailableSlot extends TimeSlot {

    private boolean continuous;

    public AvailableSlot(DayPart dayPart, LocalDateTime start, LocalDateTime end, boolean continuous)
    {
        super(dayPart, start, end);
        this.continuous = continuous;
    }
}

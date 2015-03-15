package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = "ACTUALSLOTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@NoArgsConstructor
public class ActualSlot extends TimeSlot {
    private Employee actualEmployee;

    public ActualSlot(TimeSlot.DayPart dayPart, LocalDateTime start, LocalDateTime end,
                      Employee actualEmployee) {
        super(dayPart, start, end);
        this.actualEmployee = actualEmployee;
    }
}

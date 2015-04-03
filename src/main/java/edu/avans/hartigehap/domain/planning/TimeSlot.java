package edu.avans.hartigehap.domain.planning;

import edu.avans.hartigehap.domain.DomainObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class TimeSlot extends DomainObject {
    private DayPart part;
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeSlot (TimeSlot.DayPart dayPart, LocalDateTime start, LocalDateTime end) {
        setPart(dayPart);
        setEnd(end);
        setStart(start);
    }

    public Date getStartDate () {
        return Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Getter
    public enum DayPart {

        MORNING(7, 12), AFTERNOON(12, 16), EVENING(16,
                21), NIGHT(21, 1);

        private int start;
        private int end;

        private DayPart (int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}

package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
@Entity
@Table(name = "PLANNINGS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Planning extends DomainObject {

    @ManyToOne
    private Employee employee;

    private EmployeeRole role;

    @OneToOne
    private PlannedSlot plannedSlot;

    @OneToOne
    private ActualSlot actualSlot;

    @ManyToOne
    private Employee supervisor;

    @Transient
    public boolean hasActualSlot () {
        if (actualSlot == null) {
            return false;
        }
        return true;
    }
}

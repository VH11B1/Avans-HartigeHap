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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id")
    private EmployeeRole role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "plannedSlot_id")
    private PlannedSlot plannedSlot;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "actualSlot_id")
    private ActualSlot actualSlot;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @Transient
    public boolean hasActualSlot () {
        if (actualSlot == null) {
            return false;
        }
        return true;
    }
}

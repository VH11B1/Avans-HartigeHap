package edu.avans.hartigehap.domain.planning;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.avans.hartigehap.domain.DomainObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
@Entity
@Table(name = "PLANNINGS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Planning extends DomainObject{

        private Employee employee;
        private EmployeeRole role;
        private PlannedSlot plannedSlot;
        private ActualSlot actualSlot;
}

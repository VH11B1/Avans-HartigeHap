package edu.avans.hartigehap.domain.planning;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alex on 3-3-2015.
 */
@Getter
@Setter
public class Planning {

        private Employee employee;
        private EmployeeRole role;
        private PlannedSlot plannedSlot;
        private ActualSlot actualSlot;
}

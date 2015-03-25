package edu.avans.hartigehap.domain.criteria.impl;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.planning.EmployeeRole;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class InPlannedRoleCriteria extends Criteria {
    private List<EmployeeRole> employeeRoles;

    public InPlannedRoleCriteria (EmployeeRole... a) {
        this.employeeRoles = Arrays.asList(a);
    }

    @Override
    public List<Planning> meetCriteria (final List<Planning> l) {
        List<Planning> meetCrit = new ArrayList<Planning>();
        for (Planning p : l) {
            if (p.getEmployee().getRoles().contains(p.getRole())) {
                meetCrit.add(p);
            }
        }
        return meetCrit;
    }
}

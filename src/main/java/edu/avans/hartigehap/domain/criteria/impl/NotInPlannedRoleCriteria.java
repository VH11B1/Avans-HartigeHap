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
public class NotInPlannedRoleCriteria extends Criteria {

    @Override
    public List<Planning> meetCriteria(final List<Planning> l) {
        List<Planning> meetCrit = new ArrayList<Planning>();
        for(Planning p : l){
            if(match(p)){
                meetCrit.add(p);
            }
        }
        return meetCrit;
    }

    private boolean match(final Planning p){
        for(EmployeeRole r : p.getEmployee().getRoles()){
            if(r == p.getRole()){
                return false;
            }
        }
        return true;
    }
}

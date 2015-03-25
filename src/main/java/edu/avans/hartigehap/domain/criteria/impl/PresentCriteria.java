package edu.avans.hartigehap.domain.criteria.impl;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.planning.ActualSlot;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class PresentCriteria extends Criteria {

    @Override
    public List<Planning> meetCriteria (List<Planning> l) {
        List<Planning> present = new ArrayList<Planning>();
        for (Planning p : l) {
            if (p.hasActualSlot()) {
                ActualSlot actual = p.getActualSlot();
                if (p.getEmployee().equals(actual.getActualEmployee())) {
                    present.add(p);
                }
            }
        }
        return present;
    }
}

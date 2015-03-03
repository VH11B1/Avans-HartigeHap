package edu.avans.hartigehap.domain.criteria.impl;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.planning.ActualSlot;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
public class NotPresentCriteria extends Criteria {
    @Override
    public List<Planning> meetCriteria(List<Planning> l) {
        List<Planning> notPresent = new ArrayList<Planning>();
        for (Planning p : l) {
            ActualSlot actual = p.getActualSlot();
            if (!p.getEmployee().equals(actual.getActualEmployee())) {
                notPresent.add(p);
            }
        }
        return notPresent;
    }
}

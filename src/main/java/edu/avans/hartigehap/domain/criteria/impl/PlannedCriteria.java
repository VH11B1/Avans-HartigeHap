package edu.avans.hartigehap.domain.criteria.impl;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
public class PlannedCriteria extends Criteria{
    @Override
    public List<Planning> meetCriteria(final List<Planning> l) {
        return l;
    }
}

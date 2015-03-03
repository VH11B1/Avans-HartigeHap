package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 *
 * Criteria/Filter pattern
 */
public abstract class Criteria {
    public abstract List<Planning> meetCriteria(List<Planning> l);

    public enum Type{
        PRESENT,
        NOTPRESENT,
        PLANNED,
        NOTPLANNED;
    }
}

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

    private boolean alwaysSucceed = false;

    public Criteria(){
        this(false);
    }

    public Criteria(final boolean alwaysSucceed){
        this.alwaysSucceed = alwaysSucceed;
    }

    public final boolean alwaysSucceeds(){
        return alwaysSucceed;
    }

    public enum Type{
        PRESENT,
        NOT_PRESENT,
        PLANNED,
        NOT_PLANNED,
        NOT_IN_PLANNED_ROLE;
    }
}

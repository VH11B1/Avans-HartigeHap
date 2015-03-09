package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.criteria.impl.*;
import edu.avans.hartigehap.domain.planning.Planning;
import lombok.Getter;

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
        PRESENT (new PresentCriteria()),
        PLANNED(new PlannedCriteria()),
        IN_PLANNED_ROLE(new InPlannedRoleCriteria());

        @Getter
        private Criteria criteria;

        private Type(Criteria c){
            this.criteria = c;
        }


    }
}

package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.criteria.impl.InPlannedRoleCriteria;
import edu.avans.hartigehap.domain.criteria.impl.PlannedCriteria;
import edu.avans.hartigehap.domain.criteria.impl.PresentCriteria;
import edu.avans.hartigehap.domain.planning.Planning;
import lombok.Getter;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 * <p/>
 * Criteria/Filter pattern
 */
public abstract class Criteria {
    private boolean alwaysSucceed = false;

    public Criteria () {
        this(false);
    }

    public Criteria (final boolean alwaysSucceed) {
        this.alwaysSucceed = alwaysSucceed;
    }

    public abstract List<Planning> meetCriteria (List<Planning> l);

    public final boolean alwaysSucceeds () {
        return alwaysSucceed;
    }

    public enum Type {
        PRESENT(new PresentCriteria()),
        PLANNED(new PlannedCriteria()),
        IN_PLANNED_ROLE(new InPlannedRoleCriteria());

        @Getter
        private Criteria criteria;

        private Type (Criteria c) {
            this.criteria = c;
        }


    }
}

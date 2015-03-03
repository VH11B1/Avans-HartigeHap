package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.criteria.impl.NotPresentCriteria;
import edu.avans.hartigehap.domain.criteria.impl.PlannedCriteria;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.avans.hartigehap.domain.criteria.Criteria.Type.*;

/**
 * Created by Alex on 3-3-2015.
 *
 * Builder pattern
 */
public class CriteriaBuilder {

    private static CriteriaBuilder instance;

    private static List<Criteria> criteriaList;

    // singleton
    public static CriteriaBuilder getInstance(){
        if (instance == null){
            instance = new CriteriaBuilder();
        }
        criteriaList = new ArrayList<Criteria>();
        return instance;
    }

    private CriteriaBuilder(){
        // nope
    }

    public CriteriaBuilder and(Criteria.Type... types){
        Criteria andCriteria = new AndCriteria();

        for(Criteria.Type t : types){
            andCriteria = new AndCriteria(getCriteriaByType(t),andCriteria);
        }

        criteriaList.add(andCriteria);

        // chaining
        return this;
    }

    public List<Planning> fetch(final List<Planning> allPlannings){
        List<Planning> list = allPlannings;
        for (Criteria c : criteriaList){
            list = c.meetCriteria(list);
        }
        return list;
    }

    private Criteria getCriteriaByType(Criteria.Type type){
        switch (type){
            case PLANNED : return new PlannedCriteria();
            case NOTPRESENT : return new NotPresentCriteria();
        }
        return null;
    }
}

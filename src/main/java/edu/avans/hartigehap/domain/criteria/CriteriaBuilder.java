package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

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

    public CriteriaBuilder not(final Criteria.Type type){

        criteriaList.add(new NotCriteria(type.getCriteria()));

        return this;
    }

    public CriteriaBuilder and(final Criteria.Type... types){

        Criteria[] crits = new Criteria[types.length];

        for (int i = 0; i < types.length; i++) {
            crits[i] = types[i].getCriteria();
        }

        criteriaList.add(new AndCriteria(crits)); // list cast?

        // chaining
        return this;
    }


    public CriteriaBuilder or(final Criteria.Type... types){

        Criteria[] crits = new Criteria[types.length];

        for (int i = 0; i < types.length; i++) {
            crits[i] = types[i].getCriteria(); // list cast?
        }

        criteriaList.add(new OrCriteria(crits));

        // chaining
        return this;
    }

    public CriteriaBuilder single(final Criteria.Type type){
        criteriaList.add(type.getCriteria());

        return this;
    }

    public List<Planning> fetch(final List<Planning> allPlannings){
        List<Planning> list = allPlannings;
        for (Criteria c : criteriaList){
            list = c.meetCriteria(list);
        }

        return list;
    }
}

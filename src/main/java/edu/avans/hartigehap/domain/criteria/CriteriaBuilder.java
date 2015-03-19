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
    // note: for design patterns only
    // has severe real world consequences
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

    // different from and/or/single, returns Criteria and NOT CriteriaBuilder
    // basically a converter to NOT form, needs to be inserted into the list
    // using and/or/single after conversion to NOT form
    // note: when using a mix between CriteriaType and Criteria use
    // CriteriaType.getCriteria() to convert Type to Criteria
    public Criteria not(final Criteria.Type type){
        return new NotCriteria(type.getCriteria());
    }

    // for using criteria types
    public CriteriaBuilder and(final Criteria.Type... types){

        Criteria[] crits = new Criteria[types.length];

        for (int i = 0; i < types.length; i++) {
            crits[i] = types[i].getCriteria();
        }

        criteriaList.add(new AndCriteria(crits)); // list cast?

        // chaining
        return this;
    }

    // for using criteria
    public CriteriaBuilder and(final Criteria... criterias){

        criteriaList.add(new AndCriteria(criterias)); // list cast?

        // chaining
        return this;
    }


    // for using criteria types
    public CriteriaBuilder or(final Criteria.Type... types){

        Criteria[] crits = new Criteria[types.length];

        for (int i = 0; i < types.length; i++) {
            crits[i] = types[i].getCriteria(); // list cast?
        }

        criteriaList.add(new OrCriteria(crits));

        // chaining
        return this;
    }

    // for using criteria
    public CriteriaBuilder or(final Criteria... crits){

        criteriaList.add(new OrCriteria(crits));

        // chaining
        return this;
    }

    // for using criteria types
    public CriteriaBuilder single(final Criteria.Type type){
        criteriaList.add(type.getCriteria());

        return this;
    }

    // for using criteria
    public CriteriaBuilder single(final Criteria criteria){
        criteriaList.add(criteria);

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

package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.planning.Planning;
import org.aspectj.weaver.ast.And;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
public class AndCriteria extends Criteria {
    private List<Criteria> criteriaList = new ArrayList<Criteria>();

    public AndCriteria(final boolean alwaysSucceed){
        super(alwaysSucceed);
    }

    public AndCriteria(final Criteria... a){
        criteriaList = Arrays.asList(a);
    }

    @Override
    public List<Planning> meetCriteria(final List<Planning> l) {

        if(alwaysSucceeds()){
            return l;
        }

        List<Planning> list= l;

        for(Criteria andCrit : criteriaList){
            list = andCrit.meetCriteria(list);
        }

        return list;
    }
}

package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class OrCriteria extends Criteria{
    private List<Criteria> criteriaList = new ArrayList<Criteria>();

    public OrCriteria(final boolean alwaysSucceeds){
        super(alwaysSucceeds);
    }

    public OrCriteria(final Criteria... a ){
        criteriaList = Arrays.asList(a);
    }

    @Override
    public List<Planning> meetCriteria(final List<Planning> l) {
        if(alwaysSucceeds()){
            return l;
        }
        List<Planning> list = l;

        for(Criteria orCrit : criteriaList){
                list = mergeOr(list, orCrit.meetCriteria(list));
        }
        return l;
    }

    private List<Planning> mergeOr(final List<Planning> original, final List<Planning> newList){
        for(Planning p : newList){
            if(!original.contains(p)){
                original.add(p);
            }
        }
        return original;
    }
}

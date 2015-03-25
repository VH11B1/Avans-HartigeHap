package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class OrCriteria extends Criteria {
    private List<Criteria> criteriaList = new ArrayList<>();

    public OrCriteria (final Criteria... a) {
        criteriaList = Arrays.asList(a);
    }

    @Override
    public List<Planning> meetCriteria (final List<Planning> l) {
        if (alwaysSucceeds()) {
            return l;
        }
        // if list is made equal to original, then list will always contain original elements
        // therefore, list is equal to response from first criteria
        List<Planning> list = doFirstCriteria(l);

        // already did the first, so from 1
        for (int i = 1; i < criteriaList.size(); i++) {
            list = mergeOr(list, criteriaList.get(i).meetCriteria(list));
        }

        return list;
    }

    private List<Planning> doFirstCriteria(final List<Planning> l) {
        if(criteriaList == null){
            return new ArrayList<>(); // no criteria means fail
        }
        return criteriaList.get(0).meetCriteria(l);
    }

    private List<Planning> mergeOr (List<Planning> original, final List<Planning> newList) {
        for (Planning p : newList) {
            if (!original.contains(p)) {
                original.add(p);
            }
        }
        return original;
    }
}

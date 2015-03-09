package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7-3-2015.
 */
public class NotCriteria extends Criteria {

    private Criteria notCriteria;

    // slechts een, anders gedoe met default AND/OR
    public NotCriteria(final Criteria criteria){
        this.notCriteria = criteria;
    }
    @Override
    public List<Planning> meetCriteria(final List<Planning> l) {

        List<Planning> meetCriteria = notCriteria.meetCriteria(l);
        List<Planning> notMeetCriteria = new ArrayList<>(l);

        for(Planning p : meetCriteria){
            notMeetCriteria.remove(p);
        }

        return notMeetCriteria;
    }
}

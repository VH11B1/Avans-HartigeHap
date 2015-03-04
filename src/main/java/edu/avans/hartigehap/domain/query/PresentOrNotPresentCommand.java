package edu.avans.hartigehap.domain.query;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.criteria.CriteriaBuilder;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class PresentOrNotPresentCommand extends HHCommand{
    //TODO useless, for testing only

    @Override
    public List<Planning> filter(final List<Planning> plannings) {
        CriteriaBuilder b = CriteriaBuilder.getInstance();

        b.or(Criteria.Type.PRESENT, Criteria.Type.NOT_PRESENT);

        return b.fetch(plannings);
    }
}

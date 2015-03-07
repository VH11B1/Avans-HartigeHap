package edu.avans.hartigehap.domain.query;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.criteria.CriteriaBuilder;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 3-3-2015.
 */
public class PlannedAndNotPresentCommand extends CriteriaCommand {

    @Override
    public List<Planning> fetch(final List<Planning> plannings) {
        CriteriaBuilder b = CriteriaBuilder.getInstance();

        b.and(Criteria.Type.PLANNED, Criteria.Type.NOT_PRESENT);

        return b.fetch(plannings);
    }
}

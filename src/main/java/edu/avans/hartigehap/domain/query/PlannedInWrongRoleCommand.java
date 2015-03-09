package edu.avans.hartigehap.domain.query;

import edu.avans.hartigehap.domain.criteria.Criteria;
import edu.avans.hartigehap.domain.criteria.CriteriaBuilder;
import edu.avans.hartigehap.domain.planning.Planning;

import java.util.List;

/**
 * Created by Alex on 4-3-2015.
 */
public class PlannedInWrongRoleCommand extends CriteriaCommand {

    @Override
    public List<Planning> fetch(List<Planning> plannings) {
        CriteriaBuilder b = CriteriaBuilder.getInstance();

        b.single(b.not(Criteria.Type.IN_PLANNED_ROLE));

        return b.fetch(plannings);
    }
}

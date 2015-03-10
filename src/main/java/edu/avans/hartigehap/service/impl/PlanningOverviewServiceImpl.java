package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.PlanningOverview;
import edu.avans.hartigehap.service.PlanningOverviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alex on 9-3-2015.
 */
@Service("planningOverviewService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class PlanningOverviewServiceImpl implements PlanningOverviewService{
    final Logger logger = LoggerFactory.getLogger(PlanningOverviewServiceImpl.class);

    //@Autowired
    //private PlanningRepository planningRepository;

    @Override
    public List<Planning> getCurrentWorking() {

        PlanningOverview planningOverview = new PlanningOverview();

        // List<Planning> allPlanning = planningRepository.getAllPlannedEmployees();
        List<Planning> allPlanning = planningOverview.getAllPlannedEmployees(); // populate

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getCurrentOverview();

    }
}

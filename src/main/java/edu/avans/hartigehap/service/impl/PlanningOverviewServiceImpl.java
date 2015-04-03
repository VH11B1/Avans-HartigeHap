package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.PlanningOverview;
import edu.avans.hartigehap.repository.PlanningRepository;
import edu.avans.hartigehap.service.PlanningOverviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alex on 9-3-2015.
 */
@Service("planningOverviewService")
@Repository
@Transactional
public class PlanningOverviewServiceImpl implements PlanningOverviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanningOverviewServiceImpl.class);

    @Autowired
    private PlanningRepository planningRepository;

    @Override
    public List<Planning> getCurrentWorking () {

        LOGGER.info("Fetching planning of currently working employees");

        PlanningOverview planningOverview = new PlanningOverview();

        List<Planning> allPlanning = Lists.newLinkedList(planningRepository
                .findAll());

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getCurrentOverview();

    }

    @Override
    public List<Planning> getWeekPlanning () {

        LOGGER.info("Fetching planning of employees working this week");

        PlanningOverview planningOverview = new PlanningOverview();

        List<Planning> allPlanning = Lists.newLinkedList(planningRepository
                .findAll());

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getPlannedForNextWeekInclToday();
    }

    @Override
    public List<Planning> getAllPlanningFromNow () {

        LOGGER.info("Fetching entire planning from now");

        List<Planning> allPlanning = Lists.newLinkedList(planningRepository
                .findAll());

        return allPlanning;
    }

    @Override
    public Page<Planning> getAllPlanningFromNowPageable(Pageable pageable){

        LOGGER.info("Fetching entire planning from now as pageable");

        return planningRepository.findAll(pageable);
    }
}

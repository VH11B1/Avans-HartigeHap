package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.PlanningOverview;
import edu.avans.hartigehap.service.PlanningOverviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@Transactional(rollbackFor = StateException.class)
public class PlanningOverviewServiceImpl implements PlanningOverviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanningOverviewServiceImpl.class);

    //@Autowired
    //private PlanningRepository planningRepository;

    @Override
    public List<Planning> getCurrentWorking () {

        PlanningOverview planningOverview = new PlanningOverview();

        // List<Planning> allPlanning = planningRepository.getAllPlannedEmployees();
        List<Planning> allPlanning = planningOverview.getAllPlannedEmployees(); // populate

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getCurrentOverview();

    }

    @Override
    public List<Planning> getWeekPlanning () {
        PlanningOverview planningOverview = new PlanningOverview();

        List<Planning> allPlanning = planningOverview.getAllPlannedEmployees(); // populate

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getPlannedForNextWeekInclToday();
    }

    @Override
    public List<Planning> getAllPlanningFromNow () {
        PlanningOverview planningOverview = new PlanningOverview();

        List<Planning> allPlanning = planningOverview.getAllPlannedEmployees(); // populate

        planningOverview.setPlanningList(allPlanning);

        return planningOverview.getFullOverviewFromNow();
    }

    @Override
    public Page<Planning> getAllPlanningFromNowPageable(Pageable pageable){

        // note, due to planning being dummy data there is no actual call to the repository
        // starting here the pageable will be ignored, however, up until this point
        // the code is similar to an actual pageable implementation
        // also, it returns a Page<Planning> implementation like a normal pagination method would
        //
        // in other words, in order to implement real pagination, this is the only part that
        // requires alteration
        PlanningOverview planningOverview = new PlanningOverview();

        // fetch dummydata
        List<Planning> allPlanning = planningOverview.getAllPlannedEmployees(); // populate

        // set dummydata for filter, criteria usage etc.
        planningOverview.setPlanningList(allPlanning);

        // get full overview from current date
        List<Planning> all = planningOverview.getFullOverviewFromNow();

        int startFrom = pageable.getPageNumber()*pageable.getPageSize();
        int endAt = startFrom + pageable.getPageSize();
        if(endAt > all.size()){
            endAt = all.size();
        }

        try{
            all = all.subList(startFrom,endAt);
        }catch(IndexOutOfBoundsException e){
            LOGGER.error("Can not create a sublist from index " + startFrom + " to " + endAt, e);
        }
        Page<Planning> page =  new PageImpl<>(all,pageable,all.size());
        return page;
    }
}

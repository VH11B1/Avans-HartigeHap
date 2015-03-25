package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PlannedTodayFilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter<LocalDateTime> filter;

        // test list constructor
        filter = new PlannedTodayFilter(PlanningUtil.getPlanningList());
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedTodayFilter(filter);
        assertNotNull(filter);

        // get expected results
        List<Planning> expectedList = PlanningUtil.getPlanningToday();

        filter = new PlannedTodayFilter(PlanningUtil.getPlanningList());
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());
    }

    @Test
    public void testSet() throws Exception {
        Filter<LocalDateTime> filter = new PlannedTodayFilter(new ArrayList<>());
        filter.set(LocalDateTime.now());
        assertNull(filter.getFilterItems());
    }
}
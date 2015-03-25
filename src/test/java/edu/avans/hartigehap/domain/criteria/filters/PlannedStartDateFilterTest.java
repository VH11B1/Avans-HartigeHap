package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlannedStartDateFilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter<LocalDateTime> filter;

        // test list constructor
        filter = new PlannedStartDateFilter(PlanningUtil.getPlanningList(),LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedStartDateFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter items
        filter = new PlannedStartDateFilter(filter, LocalDateTime.now());
        assertEquals(1, filter.getFilterItems().size());
        filter = new PlannedStartDateFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertEquals(2,filter.getFilterItems().size());
        filter = new PlannedStartDateFilter(filter, new LocalDateTime[]{LocalDateTime.now(),LocalDateTime.now().plusDays(1)});
        assertEquals(2,filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = PlanningUtil.getPlanningToday();

        filter = new PlannedStartDateFilter(PlanningUtil.getPlanningList(), PlanningUtil.getTestDate());
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());
    }
}
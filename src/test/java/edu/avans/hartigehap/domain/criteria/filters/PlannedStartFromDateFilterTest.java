package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlannedStartFromDateFilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter<LocalDateTime> filter;

        // test list constructor
        filter = new PlannedStartFromDateFilter(PlanningUtil.getPlanningList(),LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedStartFromDateFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter items
        filter = new PlannedStartFromDateFilter(filter, LocalDateTime.now());
        assertEquals(1, filter.getFilterItems().size());
        filter = new PlannedStartFromDateFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertEquals(1,filter.getFilterItems().size());
        filter = new PlannedStartFromDateFilter(filter, new LocalDateTime[]{LocalDateTime.now(),LocalDateTime.now().plusDays(1)});
        assertEquals(1,filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = PlanningUtil.getPlanningList();

        filter = new PlannedStartFromDateFilter(PlanningUtil.getPlanningList(), PlanningUtil.getTestDate());
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());
    }
}
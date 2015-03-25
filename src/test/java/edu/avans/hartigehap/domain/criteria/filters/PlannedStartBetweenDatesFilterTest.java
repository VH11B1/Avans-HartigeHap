package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlannedStartBetweenDatesFilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter<LocalDateTime> filter;

        // test list constructor
        filter = new PlannedStartBetweenDatesFilter(PlanningUtil.getPlanningList(),LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedStartBetweenDatesFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertNotNull(filter);

        // test filter items
        filter = new PlannedStartBetweenDatesFilter(filter, LocalDateTime.now());
        assertEquals(2, filter.getFilterItems().size());
        filter = new PlannedStartBetweenDatesFilter(filter, LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        assertEquals(2,filter.getFilterItems().size());
        filter = new PlannedStartBetweenDatesFilter(filter, new LocalDateTime[]{LocalDateTime.now(),LocalDateTime.now().plusDays(1)});
        assertEquals(2,filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = PlanningUtil.getPlanningThisWeek();

        filter = new PlannedStartBetweenDatesFilter(PlanningUtil.getPlanningList(), PlanningUtil.getTestDate(),PlanningUtil.getTestDate().plusDays(7));
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());
    }

    @Test
    public void testSet() throws Exception {
        Filter<LocalDateTime> filter = new PlannedStartBetweenDatesFilter(new ArrayList<>());
        filter.set(LocalDateTime.now());
        assertEquals(2,filter.getFilterItems().size());

        filter.set(LocalDateTime.now(), LocalDateTime.now());
        assertEquals(2,filter.getFilterItems().size());

        filter.set(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        assertEquals(2,filter.getFilterItems().size());
    }
}
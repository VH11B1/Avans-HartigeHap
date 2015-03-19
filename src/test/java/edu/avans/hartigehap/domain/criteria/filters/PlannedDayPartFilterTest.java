package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlannedDayPartFilterTest {

    @Test
    public void testFilter () throws Exception {

        Filter<TimeSlot.DayPart> filter;

        // test list constructor
        filter = new PlannedDayPartFilter(PlanningUtil.getPlanningList(), TimeSlot.DayPart.AFTERNOON);
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedDayPartFilter(filter, TimeSlot.DayPart.AFTERNOON);
        assertNotNull(filter);

        // test filter items
        filter = new PlannedDayPartFilter(filter, TimeSlot.DayPart.AFTERNOON);
        assertEquals(1,filter.getFilterItems().size());
        filter = new PlannedDayPartFilter(filter, TimeSlot.DayPart.AFTERNOON, TimeSlot.DayPart.EVENING);
        assertEquals(2,filter.getFilterItems().size());
        filter = new PlannedDayPartFilter(filter, new TimeSlot.DayPart[]{TimeSlot.DayPart.AFTERNOON, TimeSlot.DayPart.EVENING});
        assertEquals(2, filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = new ArrayList<Planning>();
        for (Planning p : PlanningUtil.getPlanningList()){
            if(TimeSlot.DayPart.AFTERNOON.equals(p.getPlannedSlot().getPart())){
                expectedList.add(p);
            }
        }

        filter = new PlannedDayPartFilter(PlanningUtil.getPlanningList(), TimeSlot.DayPart.AFTERNOON);

        // make sure filtered list contains expected items
        PlanningUtil.assertListsContainSame(expectedList, filter.filter());
    }
}
package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Employee;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlannedEmployeeFilterTest {

    @Test
    public void testFilter () throws Exception {
        Filter<Employee> filter;

        // test list constructor
        filter = new PlannedEmployeeFilter(PlanningUtil.getPlanningList(),PlanningUtil.getEmployees().get(0));
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedEmployeeFilter(filter, PlanningUtil.getEmployees().get(0));
        assertNotNull(filter);

        // test filter items
        filter = new PlannedEmployeeFilter(filter, PlanningUtil.getEmployees().get(0));
        assertEquals(1, filter.getFilterItems().size());
        filter = new PlannedEmployeeFilter(filter, PlanningUtil.getEmployees().get(0),PlanningUtil.getEmployees().get(1));
        assertEquals(2,filter.getFilterItems().size());
//        filter = new PlannedEmployeeFilter(filter, PlanningUtil.getEmployees().get(0),PlanningUtil.getEmployees().get(1));
//        assertEquals(2,filter.getFilterItems().size());

    }
}
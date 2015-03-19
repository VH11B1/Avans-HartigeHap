package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        filter = new PlannedEmployeeFilter(filter, new Employee[]{PlanningUtil.getEmployees().get(0),PlanningUtil.getEmployees().get(1)});
        assertEquals(2,filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = new ArrayList<>();
        Employee testEmployee = PlanningUtil.getEmployees().get(0);
        for (Planning p : PlanningUtil.getPlanningList()){
            if(p.getEmployee().equals(testEmployee)){
                expectedList.add(p);
            }
        }

        filter = new PlannedEmployeeFilter(filter, PlanningUtil.getEmployees().get(0));
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());

    }
}
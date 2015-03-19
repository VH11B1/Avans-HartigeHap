package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.EmployeeRole;
import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlannedRoleFilterTest {

    @Test
    public void testFilter() throws Exception {
        Filter<EmployeeRole> filter;

        // test list constructor
        filter = new PlannedRoleFilter(PlanningUtil.getPlanningList(),new EmployeeRole("SERVICE"));
        assertNotNull(filter);

        // test filter constructor
        filter = new PlannedRoleFilter(filter,new EmployeeRole("SERVICE"));
        assertNotNull(filter);

        // test filter items
        filter = new PlannedRoleFilter(filter, new EmployeeRole("SERVICE"));
        assertEquals(1, filter.getFilterItems().size());
        filter = new PlannedRoleFilter(filter, new EmployeeRole("SERVICE"),new EmployeeRole("KITCHEN"));
        assertEquals(2,filter.getFilterItems().size());
        filter = new PlannedRoleFilter(filter, new EmployeeRole[]{new EmployeeRole("SERVICE"),new EmployeeRole("KITCHEN")});
        assertEquals(2,filter.getFilterItems().size());

        // get expected results
        List<Planning> expectedList = new ArrayList<>();
        EmployeeRole testRole = new EmployeeRole("SERVICE");
        for (Planning p : PlanningUtil.getPlanningList()){
            if(p.getRole().equals(testRole)){
                expectedList.add(p);
            }
        }

        filter = new PlannedRoleFilter(PlanningUtil.getPlanningList(), testRole);
        PlanningUtil.assertListsContainSame(expectedList,filter.filter());
    }
}
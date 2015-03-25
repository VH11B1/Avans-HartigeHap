package edu.avans.hartigehap.domain.planning;

import edu.avans.hartigehap.domain.criteria.filters.PlanningUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlanningOverviewTest {

    @Test
    public void testGetAllPlannedEmployees() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlanningList();
        List<Planning> result = o.getAllPlannedEmployees();
        PlanningUtil.assertListsContainSame(expected,result);
     }

    @Test
    public void testGetAllPlannedAndNotPresentEmployees () throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlannedNotPresentList();
        List<Planning> result = o.getAllPlannedAndNotPresentEmployees();
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetWronglyPlannedEmployees() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getWronglyPlannedList();
        List<Planning> result = o.getWronglyPlannedEmployees();
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetPresentEmployees() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlannedPresentList();
        List<Planning> result = o.getPresentEmployees();
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetPlannedServiceAndKitchenEmployees() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlannedKitchenService();
        List<Planning> result = o.getPlannedServiceAndKitchenEmployees();
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetPlannedServiceForTomorrowAfternoon() throws Exception {

    }

    @Test
    public void testGetPlannedForNextWeekInclToday() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlanningThisWeek();
        List<Planning> result = o.getPlannedForNextWeekInclToday();
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetEmployeesPlannedToday() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlanningToday();

        // get employees to check for
        List<Employee> employees = new ArrayList<>();
        for (Planning p : expected) {
            if (!employees.contains(p.getEmployee())){
                employees.add(p.getEmployee());
            }
        }
        Employee[] e = new Employee[]{};
        e = employees.toArray(e);
        List<Planning> result = o.getEmployeesPlannedToday(e);
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetCurrentOverview() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlanningToday();

        // filter by current day part
        // needs a copy of current day part method in planning overview

        List<Planning> result = o.getCurrentOverview();
        //PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testGetFullOverviewFromNow() throws Exception {
        PlanningOverview o = new PlanningOverview();
        o.setPlanningList(PlanningUtil.getPlanningList());

        List<Planning> expected = PlanningUtil.getPlanningList();
        List<Planning> result = o.getFullOverviewFromNow();
        PlanningUtil.assertListsContainSame(expected,result);
    }
}
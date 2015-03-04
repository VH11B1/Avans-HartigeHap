package edu.avans.hartigehap.domain.planning;

import junit.framework.TestCase;
import org.h2.table.Plan;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PlanningOverviewTest extends TestCase {

    @Test
    public void testGetAllPlannedAndNotPresentEmployees() throws Exception {
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getAllPlannedAndNotPresentEmployees();

        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was not present and was replaced by "
                    + p.getActualSlot().getActualEmployee().getName());
        }

        System.err.println("-------------------------");
    }

    @Test
    public void testWronglyPlannedEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getWronglyPlannedEmployees();

        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was planned in the role of "
                    + p.getRole()
                    + " but is qualified for ");
            for(EmployeeRole role : p.getEmployee().getRoles()){
                System.err.print(role + " ");
            }
            System.err.println();
        }
        System.err.println("-------------------------");
    }

    @Test
    public void testGetPresentEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPresentEmployees();
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was planned and showed up for work! ");
        }
        System.err.println("-------------------------");
    }

    @Test
    public void testGetPresentOrNotPresentEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPresentOrNotPresentEmployees();
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " did or did not show up for work! ");
        }
        System.err.println("-------------------------");
    }


}
package edu.avans.hartigehap.domain.planning;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlanningOverviewTest extends TestCase {

    @Test
    public void testGetPresentOrNotPresentEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPresentOrNotPresentEmployees();
        System.err.println("------------ Present or Not Present employees, i.e. everyone-------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " did or did not show up for work! ");
        }
        System.err.println("-------------------------------------------------------------------------");
    }

    @Test
    public void testGetAllPlannedAndNotPresentEmployees() throws Exception {
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getAllPlannedAndNotPresentEmployees();

        System.err.println("-------------- Planned employees who did not show up ------------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was not present and was replaced by "
                    + p.getActualSlot().getActualEmployee().getName());
        }

        System.err.println("-------------------------------------------------------------------------");
    }

    @Test
    public void testWronglyPlannedEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getWronglyPlannedEmployees();
        System.err.println("--------------------Employees planned in wrong role----------------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was planned in the role of "
                    + p.getRole()
                    + " but is only qualified for "
                    + p.getEmployee().getRoles());
        }
        System.err.println("-------------------------------------------------------------------------");
    }

    @Test
    public void testGetPresentEmployees(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPresentEmployees();
        System.err.println("-------------------------- Present employees ----------------------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " was planned and showed up for work! ");
        }
        System.err.println("-------------------------------------------------------------------------");
    }

    @Test
    public void testFilter(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPlannedServiceAndKitchenEmployees();

        System.err.println("-------------Planned employees, filtered by Kitchen, Service------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " is planned in " + p.getRole());
        }
        System.err.println("------------------------------------------------------------------------");
    }

    @Test
    public void testDecoratedFilters(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPlannedServiceForTomorrowAfternoon();

        System.err.println("--------Planned employees, filters: Service,  Tomorrow, Afternoon--------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " is planned tomorrow as " + p.getRole() + " in the " + p.getPlannedSlot().getPart());
        }
        System.err.println("------------------------------------------------------------------------");
    }

    @Test
    public void testBetweenStartDates(){
        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getPlannedForNextWeekExclToday();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.err.println("--------Planned employees for next week excluding today--------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " is planned on " + p.getPlannedSlot().getStart().format(formatter));
        }
        System.err.println("------------------------------------------------------------------------");
    }
}
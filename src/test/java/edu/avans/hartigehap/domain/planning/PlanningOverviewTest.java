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
        List<Planning> l = overview.getPlannedForNextWeekInclToday();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.err.println("--------Planned employees for next week excluding today--------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " is planned on " + p.getPlannedSlot().getStart().format(formatter));
        }
        System.err.println("------------------------------------------------------------------------");
    }

    @Test
    public void testGetCurrentOverview(){
        // use case: het is mogelijk voor de manager om een overzicht op te vragen
        // met daarin wie op dat moment welke rol vervult

        PlanningOverview overview = new PlanningOverview();
        List<Planning> l = overview.getCurrentOverview();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.err.println("--------Planned and present employees at the current timeslot (hardcoded to morning for now)--------");
        for (Planning p : l) {
            System.err.print(p.getEmployee().getName()
                            + " is planned for " + p.getRole()
                            + " from " + p.getPlannedSlot().getStart().format(formatter) + " to " + p.getPlannedSlot().getEnd().format(formatter)
            );

            if(p.getActualSlot().getActualEmployee().equals(p.getEmployee())){
                System.err.print(" and logged in on " +  p.getActualSlot().getStart().format(formatter));
            }else{
                System.err.print(" but did not log on and was replaced by " + p.getActualSlot().getActualEmployee().getName() + " on "  +  p.getActualSlot().getStart().format(formatter));
            }
            System.err.println();
        }
        System.err.println("----------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testGetEmployeesPlannedToday(){

        PlanningOverview overview = new PlanningOverview();

        List<Employee> employees = overview.getAllEmployees();

        List<Planning> l = overview.getEmployeesPlannedToday(employees.get(0));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.err.println("----------------------Is E.M. Ployee planned today?---------------------");
        for (Planning p : l) {
            System.err.println(p.getEmployee().getName()
                    + " is planned from "
                    + p.getPlannedSlot().getStart().format(formatter)
                    + " to "
                    + p.getPlannedSlot().getEnd().format(formatter)
            + " in " + p.getRole());
        }
        System.err.println("------------------------------------------------------------------------");
    }
}
package edu.avans.hartigehap.domain.planning;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlanningOverviewConsolePrints extends TestCase {
    private List<Planning> planning = new LinkedList<>();
    private List<Employee> employees = new LinkedList<>();

    @Before
    public void setUp(){
        populate();
    }

    @Test
    public void testGetPresentOrNotPresentEmployees(){
        PlanningOverview overview = new PlanningOverview();
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
        List<Planning> l = overview.getAllPlannedAndNotPresentEmployees();

        System.err.println("-------------- Planned employees who did not show up ------------------");
        for (Planning p : l) {
            if(p.hasActualSlot()){
                System.err.println(p.getEmployee().getName()
                        + " was not present and was replaced by "
                        + p.getActualSlot().getActualEmployee().getName());
            }
        }

        System.err.println("-------------------------------------------------------------------------");
    }

    @Test
    public void testWronglyPlannedEmployees(){
        PlanningOverview overview = new PlanningOverview();
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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
        overview.setEmployeeList(employees);
        overview.setPlanningList(planning);
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

    private void populate () {
        Employee e1 = new Employee();
        e1.setName("E.M. Ployee");
        e1.setRoles(Arrays.asList(new EmployeeRole("KITCHEN"),
                new EmployeeRole("SERVICE")));

        Employee e2 = new Employee();
        e2.setName("W. Erknemer");
        e2.setRoles(Arrays.asList(new EmployeeRole("SERVICE")));

        Employee e3 = new Employee();
        e3.setName("M. Anager");
        e3.setRoles(Arrays
                .asList(new EmployeeRole("MANAGEMENT")));

        Employee e4 = new Employee();
        e4.setName("N.O.T. Planned");
        e4.setRoles(Arrays
                .asList(new EmployeeRole("SERVICE")));

        Employee e5 = new Employee();
        e5.setName("I.N. Wrong Role");
        e5.setRoles(Arrays.asList(new EmployeeRole("KITCHEN")));

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employees.add(e5);

        // employee 1 planned, employee 1 actual
        Planning p1 = new Planning();
        p1.setEmployee(e1);
        p1.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now()));
        p1.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now(), e1));
        p1.setRole((EmployeeRole) e1.getRoles().toArray()[0]);

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now(),
                LocalDateTime.now()));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now(),
                LocalDateTime.now(), e3));
        p2.setRole((EmployeeRole) e2.getRoles().toArray()[0]);

        // employee 2 planned, employee 2 actual
        Planning p8 = new Planning();
        p8.setEmployee(e2);
        p8.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now().plusDays(1),
                LocalDateTime.now()));
//        p8.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now().plusDays(1),
//                LocalDateTime.now(), e2));
        p8.setRole(new EmployeeRole("SERVICE"));

        // employee 2 planned, employee 1 actual
        Planning p3 = new Planning();
        p3.setEmployee(e2);
        p3.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now()));
        p3.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now(), e1));
        p3.setRole(new EmployeeRole("SERVICE"));


        // employee 5 planned in wrong role
        Planning p4 = new Planning();
        p4.setEmployee(e5);
        p4.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1)));
//        p4.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(1),
//                LocalDateTime.now().plusDays(1), e5));
        p4.setRole(new EmployeeRole("SERVICE"));

        // employee 3, manager planned
        Planning p6 = new Planning();
        p6.setEmployee(e3);
        p6.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now()));
        p6.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now(), e3));
        p6.setRole(new EmployeeRole("MANAGEMENT"));

        // employee 3, manager planned in far future
        Planning p7 = new Planning();
        p7.setEmployee(e3);
        p7.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(9),
                LocalDateTime.now()));
//        p7.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(9),
//                LocalDateTime.now(), e3));
        p7.setRole(new EmployeeRole("MANAGEMENT"));

        planning.add(p1);
        planning.add(p2);
        planning.add(p3);
        planning.add(p4);
        planning.add(p6);
        planning.add(p7);
        planning.add(p8);

    }
}
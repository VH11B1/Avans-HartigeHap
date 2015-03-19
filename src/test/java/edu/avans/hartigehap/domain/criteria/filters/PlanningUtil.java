package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 18-3-2015.
 */
public class PlanningUtil {

    // lists containing planning items meeting a certain condition
    private static List<Planning> planning;
    private static List<Planning> planningThisWeek;
    private static List<Planning> planningToday;
    private static List<Planning> plannedNotPresent;
    private static List<Planning> wronglyPlanned;
    private static List<Planning> plannedPresent;
    private static List<Planning> plannedKitchenService;

    private static List<Employee> employees;
    private static LocalDateTime testDate;

    static{
        planning = new ArrayList<>();
        planningThisWeek = new ArrayList<>();
        planningToday = new ArrayList<>();
        plannedNotPresent = new ArrayList<>();
        wronglyPlanned = new ArrayList<>();
        plannedPresent = new ArrayList<>();
        plannedKitchenService = new ArrayList<>();

        employees = new ArrayList<>();
        populate();
    }



    public static void assertListsContainSame (List<Planning> expected, List<Planning> real){

        // size must be the same
        assertEquals(expected.size(), real.size());

        // expected list should match real list
        for (Planning p : expected){
            assertTrue(real.contains(p));
        }
    }

    public static void assertListsCompletelyDifferent(List<Planning> expected, List<Planning> real) {

        // if a match is found, test fails
        for (Planning p : real){
            assertFalse(expected.contains(p));
        }
    }

    public static List<Planning> getPlanningList(){
        return planning;
    }
    public static List<Employee> getEmployees() {
        return employees;
    }

    private static void populate(){
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

        testDate = LocalDateTime.now();

        // employee 1 planned, employee 1 actual
        Planning p1 = new Planning();
        p1.setEmployee(e1);
        p1.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5)));
        p1.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5), e1));
        p1.setRole(new EmployeeRole("KITCHEN"));
        planningThisWeek.add(p1);
        planningToday.add(p1);
        plannedPresent.add(p1);
        plannedKitchenService.add(p1);

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, testDate,
                testDate.plusHours(5)));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON,testDate,
                testDate.plusHours(5), e3));
        p2.setRole(new EmployeeRole("SERVICE"));
        planningThisWeek.add(p2);
        planningToday.add(p2);
        plannedNotPresent.add(p2);
        plannedKitchenService.add(p2);

        // employee 2 planned, employee 2 actual
        Planning p8 = new Planning();
        p8.setEmployee(e2);
        p8.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, testDate.plusDays(1),
                testDate.plusDays(1).plusHours(5)));
//        p8.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now().plusDays(1),
//                LocalDateTime.now(), e2));
        p8.setRole(new EmployeeRole("SERVICE"));
        planningThisWeek.add(p8);
        plannedNotPresent.add(p8);
        plannedKitchenService.add(p8);

        // employee 2 planned, employee 1 actual
        Planning p3 = new Planning();
        p3.setEmployee(e2);
        p3.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5)));
        p3.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5), e1));
        p3.setRole(new EmployeeRole("SERVICE"));
        planningThisWeek.add(p3);
        planningToday.add(p3);
        plannedNotPresent.add(p3);
        plannedKitchenService.add(p3);


        // employee 5 planned in wrong role
        Planning p4 = new Planning();
        p4.setEmployee(e5);
        p4.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, testDate.plusDays(1),
                testDate.plusDays(1).plusHours(5)));
//        p4.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(1),
//                LocalDateTime.now().plusDays(1), e5));
        p4.setRole(new EmployeeRole("SERVICE"));
        planningThisWeek.add(p4);
        wronglyPlanned.add(p4);
        plannedNotPresent.add(p4);
        plannedKitchenService.add(p4);

        // employee 3, manager planned
        Planning p6 = new Planning();
        p6.setEmployee(e3);
        p6.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5)));
        p6.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, testDate,
                testDate.plusHours(5), e3));
        p6.setRole(new EmployeeRole("MANAGEMENT"));
        planningThisWeek.add(p6);
        planningToday.add(p6);
        plannedPresent.add(p6);

        // employee 3, manager planned in far future
        Planning p7 = new Planning();
        p7.setEmployee(e3);
        p7.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, testDate.plusDays(9),
                testDate.plusDays(9).plusHours(5)));
//        p7.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(9),
//                LocalDateTime.now(), e3));
        p7.setRole(new EmployeeRole("MANAGEMENT"));
        plannedNotPresent.add(p7);

        planning.add(p1);
        planning.add(p2);
        planning.add(p3);
        planning.add(p4);
        planning.add(p6);
        planning.add(p7);
        planning.add(p8);
    }

    public static LocalDateTime getTestDate(){
        return testDate;
    }

    public static List<Planning> getPlanningThisWeek(){
        return planningThisWeek;
    }
    public static List<Planning> getPlanningToday(){
        return planningToday;
    }
    public static List<Planning> getPlannedNotPresentList() {
        return plannedNotPresent;
    }
    public static List<Planning> getWronglyPlannedList() {
        return wronglyPlanned;
    }

    public static List<Planning> getPlannedPresentList() {
        return plannedPresent;
    }

    public static List<Planning> getPlannedKitchenService() {
        return plannedKitchenService;
    }
}

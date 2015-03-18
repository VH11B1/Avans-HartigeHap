package edu.avans.hartigehap.domain.criteria.filters;

import edu.avans.hartigehap.domain.planning.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 18-3-2015.
 */
public class PlanningUtil {

    private static List<Planning> planning;
    static{
        planning = new ArrayList<Planning>();
        populate();
    }

    public static void assertListsContainSame (List<Planning> expected, List<Planning> real){

        // size must be the same
        assertEquals(expected.size(), real.size());

        // expected list should match real list
        for (Planning p : real){
            assertTrue(expected.contains(p));
        }
    }

    public static List<Planning> getPlanningList(){
        return planning;
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

        // employee 1 planned, employee 1 actual
        Planning p1 = new Planning();
        p1.setEmployee(e1);
        p1.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now()));
        p1.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now(), e1));
        p1.setRole((EmployeeRole)e1.getRoles().toArray()[0]);

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now(),
                LocalDateTime.now()));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON,LocalDateTime.now(),
                LocalDateTime.now(), e3));
        p2.setRole((EmployeeRole)e2.getRoles().toArray()[0]);

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

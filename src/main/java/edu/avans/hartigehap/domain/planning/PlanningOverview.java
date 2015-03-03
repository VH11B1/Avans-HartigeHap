package edu.avans.hartigehap.domain.planning;

/**
 * Created by Alex on 3-3-2015.
 */
        import edu.avans.hartigehap.domain.query.PlannedAndNotPresentCommand;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Date;
        import java.util.List;


public class PlanningOverview {
    private List<Planning> planning;
    private List<Employee> employees;

    public PlanningOverview() {
        this.planning = new ArrayList<Planning>();
        this.employees = new ArrayList<Employee>();

        populate();


    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public List<Planning> getAllPlannedEmployees() {
        return planning;
    }

    public List<Planning> getAllPlannedAndNotPresentEmployees() {
//        Criteria planned = new PlannedCriteria();
//        Criteria notPresent = new NotPresentCriteria();
//        return new AndCriteria(planned, notPresent).meetCriteria(planning);
        return new PlannedAndNotPresentCommand().filter(planning);
    }

    public void populate() {
        Employee e1 = new Employee();
        e1.setName("E.M. Ployee");
        e1.setRoles(Arrays.asList(EmployeeRole.KITCHEN,
                EmployeeRole.SERVICE));

        Employee e2 = new Employee();
        e2.setName("E. Mployee");
        e2.setRoles(Arrays.asList(EmployeeRole.SERVICE));

        Employee e3 = new Employee();
        e3.setName("E. Manager");
        e3.setRoles(Arrays
                .asList(EmployeeRole.MANAGEMENT));

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);

        // employee 1 planned, employee 1 actual
        Planning p1 = new Planning();
        p1.setEmployee(e1);
        p1.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p1.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e1));

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e3));

        // employee 2 planned, employee 1 actual
        Planning p3 = new Planning();
        p3.setEmployee(e2);
        p3.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p3.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e1));

        planning.add(p1);
        planning.add(p2);
        planning.add(p3);

    }
}

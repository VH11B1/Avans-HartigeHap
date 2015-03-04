package edu.avans.hartigehap.domain.planning;

/**
 * Created by Alex on 3-3-2015.
 */
        import edu.avans.hartigehap.domain.query.PlannedAndNotPresentCommand;
        import edu.avans.hartigehap.domain.query.PlannedInWrongRoleCommand;
        import edu.avans.hartigehap.domain.query.PresentCommand;
        import edu.avans.hartigehap.domain.query.PresentOrNotPresentCommand;

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
        return new PlannedAndNotPresentCommand().filter(planning);
    }

    public List<Planning> getWronglyPlannedEmployees(){
        return new PlannedInWrongRoleCommand().filter(planning);
    }

    public List<Planning> getPresentEmployees(){
        return new PresentCommand().filter(planning);
    }

    // TODO useless, for testing purposes
    public List<Planning> getPresentOrNotPresentEmployees(){
        return new PresentOrNotPresentCommand().filter(planning);
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

        Employee e4 = new Employee();
        e4.setName("I. NotPlanned");
        e4.setRoles(Arrays
                .asList(EmployeeRole.SERVICE));

        Employee e5 = new Employee();
        e5.setName("I. Wrongrole");
        e5.setRoles(Arrays.asList(EmployeeRole.KITCHEN));

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employees.add(e5);

        // employee 1 planned, employee 1 actual
        Planning p1 = new Planning();
        p1.setEmployee(e1);
        p1.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p1.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e1));
        p1.setRole(e1.getRoles().get(0));

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e3));
        p2.setRole(e2.getRoles().get(0));

        // employee 2 planned, employee 1 actual
        Planning p3 = new Planning();
        p3.setEmployee(e2);
        p3.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p3.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e1));
        p3.setRole(e2.getRoles().get(0));


        // employee 5 planned in wrong role
        Planning p4 = new Planning();
        p4.setEmployee(e5);
        p4.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date()));
        p4.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, new Date(),
                new Date(), e5));
        p4.setRole(EmployeeRole.SERVICE);

        planning.add(p1);
        planning.add(p2);
        planning.add(p3);
        planning.add(p4);

    }
}

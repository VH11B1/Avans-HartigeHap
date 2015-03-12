package edu.avans.hartigehap.domain.planning;

/**
 * Created by Alex on 3-3-2015.
 */
        import edu.avans.hartigehap.domain.criteria.filters.*;
        import edu.avans.hartigehap.domain.query.*;

        import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.List;

/**
 * How to add a new getSomethingMeetingThisAndThatCondition method to PlanningOverview:
 *
 * - Create a new Criteria in criteria.impl, for instance PresentCriteria
 * - Create a new Criteria Type in Criteria, for instance PRESENT (new PresentCriteria())
 * - Create a new Command in criteria.query, for instance PresentCommand
 * - Build a query in the Command class using CriteriaBuilder and the Criteria Type, for
 *   instance b.single(Criteria.Type.PRESENT)
 * - Create a new method in PlanningOverview returning the command's
 *   fetch(List<Planning>) method, for instance getPresentEmployees()
 *
 * - Optional, apply a filter before returning the above, to create a filter:
 * - Create a new FilterDecorator implementation in criteria.filters, for instance PlanningRoleFilter
 * - Implement constructors and filter() method, use original.filter() to stack filters
 * - Call the filter in the PlanningOverview method, for instance getPlannedServiceAndKitchenEmployees()
 * - Add list to first filter, decorate with n other filters, finally call filter() to return a filtered list
 *
 * Note: Filters and Criteria can be swapped, i.e. filter first, then match on criteria
 *
 * Patterns used by above steps:
 *
 * - Criteria pattern > (Criteria, combining criteria in CriteriaBuilder, etc.)
 * - Factory pattern > (Criteria.Type enum)
 * - Builder pattern > (CriteriaBuilder)
 * - Singleton pattern > (CriteriaBuilder is singleton)
 * - Command pattern > (CriteriaCommand, combining CriteriaBuilder actions)
 * - Decorator pattern > (Filter, FilterDecorator, etc.)
 */
public class PlanningOverview {
    private List<Planning> planning;
    private List<Employee> employees;

    public PlanningOverview() {
        this.planning = new ArrayList<Planning>();
        this.employees = new ArrayList<Employee>();

        populate();
    }

    public void setPlanningList(List<Planning> list){
        this.planning = list;
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public List<Planning> getAllPlannedEmployees() {
        return planning;
    }

    public List<Planning> getAllPlannedAndNotPresentEmployees() {
        return new PlannedAndNotPresentCommand().fetch(planning);
    }

    public List<Planning> getWronglyPlannedEmployees(){
        return new PlannedInWrongRoleCommand().fetch(planning);
    }

    public List<Planning> getPresentEmployees(){
        return new PresentCommand().fetch(planning);
    }


    // TODO inefficient example for demonstrating decorator only
    public List<Planning> getPlannedServiceAndKitchenEmployees(){

        List<Planning> list = planning; // normally from a command

        // filters will override each other
        // this one will keep only service, kitchen and management employees
        Filter<EmployeeRole> skmFilter = new PlannedRoleFilter(planning, new EmployeeRole("SERVICE"),new EmployeeRole("KITCHEN"), new EmployeeRole("MANAGEMENT"));

        // this one will filter out managers
        Filter<EmployeeRole> skFilter = new PlannedRoleFilter(skmFilter,new EmployeeRole("SERVICE"),new EmployeeRole("KITCHEN"));

        // contains only service and kitchen
        return skFilter.filter();
    }

    // TODO inefficient example for demonstration purposes only
    public List<Planning> getPlannedServiceForTomorrowAfternoon(){
        List<Planning> list = planning; // all planned

        // filter by tomorrow
        Filter<LocalDateTime> dateFilter = new PlannedStartDateFilter(planning,LocalDateTime.now().plusDays(1));

        // filter by afternoon
        Filter<TimeSlot.DayPart> partFilter = new PlannedDayPartFilter(dateFilter,TimeSlot.DayPart.AFTERNOON);

        // filter by service
        Filter<EmployeeRole> roleFilter = new PlannedRoleFilter(partFilter,new EmployeeRole("SERVICE"));

        //service planning tomorrow afternoon
        return roleFilter.filter();
    }

    public List<Planning> getPlannedForNextWeekInclToday(){
        List<Planning> list = planning; // all planned

        // filter by next week, starting today
        Filter<LocalDateTime> dateFilter = new PlannedStartBetweenDatesFilter(planning,LocalDateTime.now().plusDays(1).minusMinutes(5),LocalDateTime.now().plusDays(7));
        // minus 5 minutes or the now() calls below will be different from this now() call,
        // shifting tomorrow into today causing expected items not to show

        //planning tomorrow and days after
        return dateFilter.filter();
    }

    public List<Planning> getEmployeesPlannedToday(final Employee... employees){
        List<Planning> list = planning; // all planned

        Filter<LocalDateTime> dateFilter = new PlannedTodayFilter(planning);
        Filter<Employee> employeeFilter = new PlannedEmployeeFilter(dateFilter,employees);

        return employeeFilter.filter();
    }

    // TODO useless, for testing purposes
    public List<Planning> getPresentOrNotPresentEmployees(){
        return new PresentOrNotPresentCommand().fetch(planning);
    }


    // use case: het is mogelijk voor de manager om een overzicht op te vragen
    // met daarin wie op dat moment welke rol vervult
    public List<Planning> getCurrentOverview(){

        TimeSlot.DayPart partOfDay = getCurrentDayPart();
        Calendar c = Calendar.getInstance();

        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        // start of today is now() - current hours - current minutes
        LocalDateTime startOfToday = LocalDateTime.now().minusHours(hours).minusMinutes(minutes);

        // end of today is start of today + one day
        LocalDateTime endOfToday = startOfToday.plusDays(1);

        return new PlannedDayPartFilter(new PlannedStartBetweenDatesFilter(planning,startOfToday, endOfToday), partOfDay).filter() ;
    }

    public List<Planning> getFullOverviewFromNow(){
        return new PlannedStartFromDateFilter(planning,LocalDateTime.now()).filter();
    }

    // TODO implement for realsies
    private TimeSlot.DayPart getCurrentDayPart(){
        return TimeSlot.DayPart.MORNING;
    }

    public void populate() {
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
        p1.setRole(e1.getRoles().get(0));

        // employee 2 planned, employee 3 actual
        Planning p2 = new Planning();
        p2.setEmployee(e2);
        p2.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now(),
                LocalDateTime.now()));
        p2.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON,LocalDateTime.now(),
                LocalDateTime.now(), e3));
        p2.setRole(e2.getRoles().get(0));

        // employee 2 planned, employee 2 actual
        Planning p8 = new Planning();
        p8.setEmployee(e2);
        p8.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now().plusDays(1).plusMinutes(5),
                LocalDateTime.now()));
        p8.setActualSlot(new ActualSlot(TimeSlot.DayPart.AFTERNOON, LocalDateTime.now().plusDays(1).plusMinutes(5),
                LocalDateTime.now(), e2));
        p8.setRole(e2.getRoles().get(0));

        // employee 2 planned, employee 1 actual
        Planning p3 = new Planning();
        p3.setEmployee(e2);
        p3.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now()));
        p3.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now(),
                LocalDateTime.now(), e1));
        p3.setRole(e2.getRoles().get(0));


        // employee 5 planned in wrong role
        Planning p4 = new Planning();
        p4.setEmployee(e5);
        p4.setPlannedSlot(new PlannedSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1)));
        p4.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1), e5));
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
        p7.setActualSlot(new ActualSlot(TimeSlot.DayPart.MORNING, LocalDateTime.now().plusDays(9),
                LocalDateTime.now(), e3));
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

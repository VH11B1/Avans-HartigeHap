package edu.avans.hartigehap.domain.planning;

/**
 * Created by Alex on 3-3-2015.
 */

import edu.avans.hartigehap.domain.criteria.filters.*;
import edu.avans.hartigehap.domain.query.PlannedAndNotPresentCommand;
import edu.avans.hartigehap.domain.query.PlannedInWrongRoleCommand;
import edu.avans.hartigehap.domain.query.PresentCommand;
import edu.avans.hartigehap.domain.query.PresentOrNotPresentCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * How to add a new getSomethingMeetingThisAndThatCondition method to PlanningOverview:
 * <p/>
 * - Create a new Criteria in criteria.impl, for instance PresentCriteria
 * - Create a new Criteria Type in Criteria, for instance PRESENT (new PresentCriteria())
 * - Create a new Command in criteria.query, for instance PresentCommand
 * - Build a query in the Command class using CriteriaBuilder and the Criteria Type, for
 * instance b.single(Criteria.Type.PRESENT)
 * - Create a new method in PlanningOverview returning the command's
 * fetch(List<Planning>) method, for instance getPresentEmployees()
 * <p/>
 * - Optional, apply a filter before returning the above, to create a filter:
 * - Create a new FilterDecorator implementation in criteria.filters, for instance PlanningRoleFilter
 * - Implement constructors and filter() method, use original.filter() to stack filters
 * - Call the filter in the PlanningOverview method, for instance getPlannedServiceAndKitchenEmployees()
 * - Add list to first filter, decorate with n other filters, finally call filter() to return a filtered list
 * <p/>
 * Note: Filters and Criteria can be swapped, i.e. filter first, then match on criteria
 * <p/>
 * Patterns used by above steps:
 * <p/>
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

    public PlanningOverview () {
        this.planning = new ArrayList<Planning>();
        this.employees = new ArrayList<Employee>();

        //populate();
//        PlanningPopulatorServiceImpl p = new PlanningPopulatorServiceImpl();
//        this.planning = p.generateRandomPlanning();
//        this.employees = p.getEmployeeList();
    }

    public void addEmployee (Employee employee) {
        this.employees.add(employee);
    }

    public void addPlanning (Planning p) {
        this.planning.add(p);
    }

    public void setPlanningList (List<Planning> list) {
        this.planning = list;
    }
    public void setEmployeeList (List<Employee> list) {
        this.employees = list;
    }

    public List<Employee> getAllEmployees () {
        return employees;
    }

    public List<Planning> getAllPlannedEmployees () {
        return planning;
    }

    public List<Planning> getAllPlannedAndNotPresentEmployees () {
        return new PlannedAndNotPresentCommand().fetch(planning);
    }

    public List<Planning> getWronglyPlannedEmployees () {
        return new PlannedInWrongRoleCommand().fetch(planning);
    }

    public List<Planning> getPresentEmployees () {
        return new PresentCommand().fetch(planning);
    }


    // TODO inefficient example for demonstrating decorator only
    public List<Planning> getPlannedServiceAndKitchenEmployees () {

        List<Planning> list = planning; // normally from a command

        // filters will override each other
        // this one will keep only service, kitchen and management employees
        Filter<EmployeeRole> skmFilter = new PlannedRoleFilter(list, new EmployeeRole("SERVICE"), new EmployeeRole("KITCHEN"), new EmployeeRole("MANAGEMENT"));

        // this one will filter out managers
        Filter<EmployeeRole> skFilter = new PlannedRoleFilter(skmFilter, new EmployeeRole("SERVICE"), new EmployeeRole("KITCHEN"));

        // contains only service and kitchen
        return skFilter.filter();
    }

    // TODO inefficient example for demonstration purposes only
    public List<Planning> getPlannedServiceForTomorrowAfternoon () {
        List<Planning> list = planning; // all planned

        // filter by tomorrow
        Filter<LocalDateTime> dateFilter = new PlannedStartDateFilter(list, LocalDateTime.now().plusDays(1));

        // filter by afternoon
        Filter<TimeSlot.DayPart> partFilter = new PlannedDayPartFilter(dateFilter, TimeSlot.DayPart.AFTERNOON);

        // filter by service
        Filter<EmployeeRole> roleFilter = new PlannedRoleFilter(partFilter, new EmployeeRole("SERVICE"));

        //service planning tomorrow afternoon
        return roleFilter.filter();
    }

    public List<Planning> getPlannedForNextWeekInclToday () {
        List<Planning> list = planning; // all planned

        // filter by next week, starting today
        Filter<LocalDateTime> dateFilter = new PlannedStartBetweenDatesFilter(list, LocalDateTime.now().plusDays(1).minusMinutes(5), LocalDateTime.now().plusDays(7));
        // minus 5 minutes or the now() calls below will be different from this now() call,
        // shifting tomorrow into today causing expected items not to show

        //planning tomorrow and days after
        return dateFilter.filter();
    }

    public List<Planning> getEmployeesPlannedToday (final Employee... employees) {
        List<Planning> list = planning; // all planned

        Filter<LocalDateTime> dateFilter = new PlannedTodayFilter(list);
        Filter<Employee> employeeFilter = new PlannedEmployeeFilter(dateFilter, employees);

        return employeeFilter.filter();
    }

    // TODO useless, for testing purposes
    public List<Planning> getPresentOrNotPresentEmployees () {
        return new PresentOrNotPresentCommand().fetch(planning);
    }


    // use case: het is mogelijk voor de manager om een overzicht op te vragen
    // met daarin wie op dat moment welke rol vervult
    public List<Planning> getCurrentOverview () {

        TimeSlot.DayPart partOfDay = getCurrentDayPart();
        Calendar c = Calendar.getInstance();

        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        // start of today is now() - current hours - current minutes
        LocalDateTime startOfToday = LocalDateTime.now().minusHours(hours).minusMinutes(minutes);

        // end of today is start of today + one day
        LocalDateTime endOfToday = startOfToday.plusDays(1);

        return new PlannedDayPartFilter(new PlannedStartBetweenDatesFilter(planning, startOfToday, endOfToday), partOfDay).filter();
    }

    public List<Planning> getFullOverviewFromNow () {
        return new PlannedStartFromDateFilter(planning, LocalDateTime.now()).filter();
    }

    // TODO implement for realsies
    private TimeSlot.DayPart getCurrentDayPart () {
        return TimeSlot.DayPart.MORNING;
    }
}

package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.NotificationSubject;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.PlanningOverview;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("employeeService")
@Repository
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly=true)
    public List<Employee> findAll()
    {
        return Lists.newArrayList(employeeRepository.findAll());
    }

    @Transactional(readOnly=true)
    public List<Employee> findByRestaurant(Restaurant worksAt)
    {
        return Lists.newArrayList(employeeRepository.findByRestaurant(worksAt));
    }

    @Transactional(readOnly=true)
    public Employee findById(Long id)
    {
        return employeeRepository.findOne(id);
    }

    public Employee save(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public void delete(Long id)
    {
        employeeRepository.delete(id);
    }

    public void checkTiming(String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        PlanningOverview overview = new PlanningOverview();

        if (employee != null) {
            overview.addEmployee(employee);

            for (Planning p : employee.getPlannings()) {
                overview.addPlanning(p);
            }

            List<Planning> l = overview.getEmployeesPlannedToday(employee);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            Date currentDate = Calendar.getInstance().getTime();
            boolean late = true;
            long diff = Long.MIN_VALUE;
            Employee supervisor = null;
            for (Planning p : l) {
                long tempDiff = TimeUnit.MILLISECONDS.toMinutes(p.getPlannedSlot().getStartDate().getTime() - currentDate.getTime());

                if (tempDiff >= 30 && p.getPlannedSlot().getStartDate().after(currentDate)) {
                    late = false;
                } else if (tempDiff > diff) {
                    diff = tempDiff;
                    supervisor = p.getSupervisor();
                }
            }

            if (late && l.size() > 0) {
                NotificationSubject.getInstance().notifyObservers(employee, supervisor, employee.getName() + " te laat", employee.getName() + " is " + -diff + " minuten te laat aangemeld.");
            }
        }
    }
}

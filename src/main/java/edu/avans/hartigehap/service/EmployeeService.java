package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.planning.Employee;

import java.util.List;

/**
 * Created by Bob on 09/03/15.
 */
public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(Long id);
    Employee save(Employee employee);
    void delete(Long id);

}

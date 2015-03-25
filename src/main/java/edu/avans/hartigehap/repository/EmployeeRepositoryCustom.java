package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.planning.Employee;

/**
 * Created by Mark on 10-3-2015.
 */
public interface EmployeeRepositoryCustom {
    public Employee findEmployeeByUsername (String username);
    public Employee saveEmployeeAndUser(Employee employee);
}

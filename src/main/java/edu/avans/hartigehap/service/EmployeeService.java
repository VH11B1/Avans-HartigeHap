package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.planning.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    List<Employee> findByRestaurant(Restaurant restaurant);
    Employee findByUsername(String username);
    Employee findById(Long id);
    Employee save(Employee employee);
    void delete(Long id);
    void checkTiming(String username);

}

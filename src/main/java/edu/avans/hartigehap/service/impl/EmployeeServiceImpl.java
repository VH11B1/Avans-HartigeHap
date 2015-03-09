package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Employee> findByRestaurant(Restaurant restaurant)
    {
        return Lists.newArrayList(employeeRepository.findByRestaurant(restaurant));
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

}

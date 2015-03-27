package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.util.List;

public class PlanningPopulatorTest {

    @Test
    public void testGetEmployeeList () throws Exception {
        PlanningPopulator populator = new PlanningPopulator();
        List<Employee> l = populator.getEmployeeList();
        for (Employee employee : l) {
            System.err.println(employee.getName() + " " + employee.getUsername() + " " + employee.getEmail() + " " + employee.getRoles() + " " + employee.getHoursPerMonth());
        }

        List<Planning> list = populator.generateRandomPlanning();
        for (Planning p : list) {
            System.err.println(p.getEmployee() + " planned on " + p.getPlannedSlot().getStart());
        }

    }
}
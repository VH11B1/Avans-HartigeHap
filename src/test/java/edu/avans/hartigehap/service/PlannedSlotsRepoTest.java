package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.planning.PlannedSlot;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.TimeSlot;
import edu.avans.hartigehap.repository.EmployeeRepository;
import edu.avans.hartigehap.repository.PlannedSlotsRepository;
import edu.avans.hartigehap.repository.PlanningRepo;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Mark on 11-3-2015.
 */
public class PlannedSlotsRepoTest extends AbstractTransactionRollbackTest {
    @Autowired
    PlannedSlotsRepository plannedSlotsRepository;

    @Autowired
    PlanningRepo planningRepo;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void save(){

        String str = "11-03-2015 11:15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        PlannedSlot slot = new PlannedSlot(TimeSlot.DayPart.AFTERNOON,dateTime,dateTime.plusMinutes(50));
        Planning planning = new Planning();
        planning.setEmployee(employeeRepository.findById(1));
        planning.setSupervisor(planning.getEmployee());
        plannedSlotsRepository.save(slot);
        planning.setPlannedSlot(plannedSlotsRepository.findById(1));
        planningRepo.save(planning);
    }
}

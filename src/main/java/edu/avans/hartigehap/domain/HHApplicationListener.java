package edu.avans.hartigehap.domain;

import edu.avans.hartigehap.domain.planning.Employee;
import edu.avans.hartigehap.domain.planning.NotificationSubject;
import edu.avans.hartigehap.domain.planning.Planning;
import edu.avans.hartigehap.domain.planning.PlanningOverview;
import edu.avans.hartigehap.repository.EmployeeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mark on 8-3-2015.
 */
public class HHApplicationListener implements AuthenticationSuccessHandler {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        PlanningOverview overview = new PlanningOverview();

        Employee employee = employeeRepository.findEmployeeByUsername(userDetails.getUsername());
        overview.addEmployee(employee);

        for (Planning p : employee.getPlannings()) {
            overview.addPlanning(p);
        }

        List<Planning> l = overview.getEmployeesPlannedToday();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Date currentDate = Calendar.getInstance().getTime();
        boolean late = true;
        long diff = Long.MIN_VALUE;
        Employee supervisor = null;
        for (Planning p : l) {
            long tempDiff = TimeUnit.MILLISECONDS.toMinutes(p.getPlannedSlot().getStartDate().getTime() - currentDate.getTime());

            if (tempDiff >= 30 && p.getPlannedSlot().getStartDate().after(currentDate))
                late = false;
            else if(tempDiff > diff){
                diff = tempDiff;
                supervisor = p.getSupervisor();
            }
        }

        if(late && l.size() > 0){
            NotificationSubject.getInstance().notifyObservers(employee, supervisor, employee.getName() +" te laat", employee.getName() + " is " + diff + " minuten te laat aangemeld.");
        }
        httpServletResponse.sendRedirect("/");
    }
}


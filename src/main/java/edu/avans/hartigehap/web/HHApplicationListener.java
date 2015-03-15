package edu.avans.hartigehap.web;

import edu.avans.hartigehap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Mark on 8-3-2015.
 */
public class HHApplicationListener implements ApplicationListener<AuthenticationSuccessEvent>  {

    @Autowired
    EmployeeService employeeService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        UserDetails userDetails = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
        employeeService.checkTiming(userDetails.getUsername());
    }
}


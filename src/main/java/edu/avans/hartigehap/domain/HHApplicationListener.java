package edu.avans.hartigehap.domain;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Mark on 8-3-2015.
 */
@Component
public class HHApplicationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        /*UserDetails userDetails = (UserDetails) event.getAuthentication()
                .getPrincipal();
        System.out.println("Successed login:" + userDetails.getUsername());*/

    }
}


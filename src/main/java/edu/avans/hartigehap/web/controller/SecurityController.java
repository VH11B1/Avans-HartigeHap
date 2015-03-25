package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.web.form.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;


@Controller
@RequestMapping("/security")
public class SecurityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/loginfail")
    public String loginFail (Model uiModel, Locale locale) {
        LOGGER.info("Login failed detected");
        uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_login_fail", new Object[]{}, locale)));
        return "restaurants/index";
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess (Model uiModel, Locale locale) {
        LOGGER.info("Login success detected");
        return "restaurants/index";
    }

}

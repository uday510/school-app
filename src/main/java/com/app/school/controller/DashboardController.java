package com.app.school.controller;

import com.app.school.model.Person;
import com.app.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @Value("${school.app.pageSize}")
    private int pageSize;

    @Value("${school.contact.successMessage}")
    private String message;

    @Autowired
    private Environment environment;

    @Value("${app.pageSize}")
    private String defaultPageSize;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (null != person.getClassroom() && null != person.getClassroom().getName()) {
            model.addAttribute("enrolledClass", person.getClassroom().getName());
        }

        session.setAttribute("loggedInUser", person);
        logMessages();
        return "dashboard.html";
    }

    private void logProperties() {
        log.info(environment.getProperty("school.app.pageSize"));
        log.info(environment.getProperty("school.contact.successMessage"));
    }

    private void logMessages() {
        log.error("Error message from the Dashboard Controller");
        log.warn("Warn message from the Dashboard Controller");
        log.info("Info message from the Dashboard Controller");
        log.debug("Debug message from the Dashboard Controller");
        log.trace("Trace message from the Dashboard Controller");

        log.error("defaultPageSize value with @Value annotation is : " + defaultPageSize);
        log.error("successMsg value with @Value annotation is : " + message);

        log.error(environment.getProperty("app.pageSize"));
        log.error(environment.getProperty("app.successMessage"));
        log.error(environment.getProperty("JAVA_HOME"));
    }

}

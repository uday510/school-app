package com.app.school.controller;

import com.app.school.model.Person;
import com.app.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (null != person.getClassroom() && null != person.getClassroom().getName()) {
            model.addAttribute("enrolledClass", person.getClassroom().getName());
        }

        session.setAttribute("loggedInUser", person);

        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error msg from Dashboard controller");
        log.warn("Warn msg from Dashboard controller");
        log.info("Info msg from Dashboard controller");
        log.debug("Debug msg from Dashboard controller");
        log.trace("Trace msg from Dashboard controller");

        log.error("default pageSize " + pageSize);
        log.error("default successMsg " + message);

        log.info(environment.getProperty("school.app.pageSize"));
        log.info(environment.getProperty("school.contact.successMessage"));
    }

    private void logProperties() {
        log.info(environment.getProperty("school.app.pageSize"));
        log.info(environment.getProperty("school.contact.successMessage"));
    }

}

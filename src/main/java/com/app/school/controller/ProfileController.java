package com.app.school.controller;

import com.app.school.model.Address;
import com.app.school.model.Person;
import com.app.school.model.Profile;
import com.app.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        Profile profile = new Profile();

        profile.setName(loggedInUser.getName());
        profile.setMobileNumber(loggedInUser.getMobileNumber());
        profile.setEmail(loggedInUser.getEmail());

        if (loggedInUser.getAddress() != null && loggedInUser.getAddress().getAddressId() > 0) {
            profile.setAddress1(loggedInUser.getAddress().getAddress1());
            profile.setAddress2(loggedInUser.getAddress().getAddress2());
            profile.setCity(loggedInUser.getAddress().getCity());
            profile.setState(loggedInUser.getAddress().getState());
            profile.setZipCode(loggedInUser.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session) {

        if (errors.hasErrors()) {
            return "profile.html";
        }

        Person person = (Person) session.getAttribute("loggedInUser");

        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if (person.getAddress() == null || !(person.getAddress().getAddressId() > 0)) {
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

        Person savedPerson = personRepository.save(person);
        session.setAttribute("loggedInUser", savedPerson);
        return "redirect:/displayProfile";
    }
}

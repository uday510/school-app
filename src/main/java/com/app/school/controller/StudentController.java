package com.app.school.controller;

import ch.qos.logback.core.model.Model;
import com.app.school.model.Classroom;
import com.app.school.model.Person;
import com.app.school.repository.ClassroomRepository;
import com.app.school.repository.CoursesRepository;
import com.app.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession httpSession) {
        Person person = (Person) httpSession.getAttribute("loggedInUser");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return  modelAndView;
    }

}

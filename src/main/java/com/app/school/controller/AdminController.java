package com.app.school.controller;

import com.app.school.model.Classroom;
import com.app.school.model.Courses;
import com.app.school.model.Person;
import com.app.school.repository.ClassroomRepository;
import com.app.school.repository.CoursesRepository;
import com.app.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<Classroom> classrooms = classroomRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("classrooms", classrooms);
        modelAndView.addObject("classroom", new Classroom());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView displayClassroom(Model model, @ModelAttribute("classroom") Classroom classroom) {
        classroomRepository.save(classroom);
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClassroom(Model model, @RequestParam int id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);

        if (classroom.isPresent()) {
            List<Person> persons = new ArrayList<>(classroom.get().getPersons());

            for (Person person : persons) {
                person.setClassroom(null);
                personRepository.save(person);
            }

            classroomRepository.deleteById(id);
        }

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession httpSession, @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<Classroom> classroomOpt = classroomRepository.findById(classId);

        if (classroomOpt.isPresent()) {
            Classroom classroom = classroomOpt.get();

            List<Person> personsCopy = new ArrayList<>(classroom.getPersons());

            for (Person person : personsCopy) {
                System.out.println(person.getName());
            }

            modelAndView.addObject("classroom", classroom);
            modelAndView.addObject("persons", personsCopy);
            modelAndView.addObject("person", new Person());
            httpSession.setAttribute("classroom", classroom);
        }

        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid email address");
        }

        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + classroom.getClassId() + "&error=true");
            return modelAndView;
        }

        if (!classroom.getPersons().contains(personEntity)) {
            personEntity.setClassroom(classroom);
            personRepository.save(personEntity);
            classroom.getPersons().add(personEntity);
            classroomRepository.save(classroom);
        }

        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + classroom.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model  model, @RequestParam int personId, HttpSession httpSession) {
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setClassroom(null);
        classroom.getPersons().remove(person.get());
        Classroom updatedClassroom =  classroomRepository.save(classroom);
        httpSession.setAttribute("classroom", updatedClassroom);
        ModelAndView modelAndView =  new ModelAndView("redirect:/admin/displayStudents?classId="+classroom.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        List<Courses> courses = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        model.addAttribute("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession httpSession, @RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses =  coursesRepository.findById(id);
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        httpSession.setAttribute("courses", courses.get());
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid email address");
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        httpSession.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession httpSession) {
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.ifPresent(personEntity -> {
            personEntity.getCourses().remove(courses);
        });
        courses.getPersons().remove(person.get());
        personRepository.save(person.get());
        httpSession.setAttribute("courses", courses);
        return new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
    }

}

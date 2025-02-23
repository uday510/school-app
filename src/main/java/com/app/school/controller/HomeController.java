package com.app.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(Model model) {
        model.addAttribute("username", "user");
        return "home.html";
    }
}

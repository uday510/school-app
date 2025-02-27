package com.app.school.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception) {
        ModelAndView errorPage = new ModelAndView("error");
        errorPage.addObject("errMsg", exception.getMessage());
        return errorPage;
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionHandler(Exception exception) {
        ModelAndView errorPage = new ModelAndView("error");
        errorPage.addObject("errMsg", exception.getMessage());
        return errorPage;
    }

}

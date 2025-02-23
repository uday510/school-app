package com.app.school.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/courses").setViewName("courses");
        viewControllerRegistry.addViewController("/contact").setViewName("contact");
        viewControllerRegistry.addViewController("/about").setViewName("about");
    }

}

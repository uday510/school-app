package com.app.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.app.school.repository")
@EntityScan("com.app.school.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

}

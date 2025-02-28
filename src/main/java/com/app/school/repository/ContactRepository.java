package com.app.school.repository;

/*
@Repository stereotype annotation is used to add a bean od this class
type to the spring context and indicate that given bean is used to perform
DB related operations
* */

import com.app.school.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        String SQL = "INSERT INTO CONTACT_MSG (" +
                "NAME, MOBILE_NUM, EMAIL, SUBJECT, MESSAGE, STATUS," +
                "CREATED_AT, CREATED_BY) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(SQL,
                contact.getName(), contact.getMobileNum(),
                contact.getEmail(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(),
                contact.getCreatedAt(), contact.getCreatedBy()
                );
    }
}

package com.app.school.repository;

/*
@Repository stereotype annotation is used to add a bean od this class
type to the spring context and indicate that given bean is used to perform
DB related operations
* */

import com.app.school.model.Contact;
import com.app.school.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contact> findMsgsWithStatus(String status) {
        String SQL = "SELECT * FROM contact_msg WHERE status = ?";

        return jdbcTemplate.query(SQL, preparedStatement ->
                preparedStatement.setString(1, status), new ContactRowMapper());
    }

    public int saveContactMsg(Contact contact) {
        String SQL = "INSERT INTO contact_msg (" +
                "name, mobile_num, email, subject, message, status," +
                "created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(SQL,
                contact.getName(), contact.getMobileNum(),
                contact.getEmail(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(),
                contact.getCreatedAt(), contact.getCreatedBy()
                );
    }

    public int updateMgStatus(int contactId, String status, String updatedBy) {
        String SQL = "UPDATE contact_msg SET status = ?, updated_by = ?, updated_at = ? WHERE contact_id = ?";
        return jdbcTemplate.update(SQL, preparedStatement -> {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, updatedBy);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, contactId);
        }
        );
    }
}

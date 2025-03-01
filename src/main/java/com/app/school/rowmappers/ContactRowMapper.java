package com.app.school.rowmappers;

import com.app.school.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet resultSet, int numRows) throws SQLException {
        Contact contact = new Contact();
        contact.setContactId(resultSet.getInt("CONTACT_ID"));
        contact.setName(resultSet.getString("NAME"));
        contact.setMobileNum(resultSet.getString("MOBILE_NUM"));
        contact.setEmail(resultSet.getString("EMAIL"));
        contact.setSubject(resultSet.getString("SUBJECT"));
        contact.setMessage(resultSet.getString("MESSAGE"));
        contact.setStatus(resultSet.getString("STATUS"));
        contact.setCreatedAt(resultSet.getTimestamp("CREATED_AT").toLocalDateTime());
        contact.setCreatedBy(resultSet.getString("CREATED_BY"));

        if (null != resultSet.getTimestamp("UPDATED_AT")) {
            contact.setUpdateAt(resultSet.getTimestamp("UPDATED_AT").toLocalDateTime());
        }

        contact.setUpdatedBy(resultSet.getString("UPDATED_BY"));
        return contact;
    }

}

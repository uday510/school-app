package com.app.school.service;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Contact;
import com.app.school.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        log.info("Contact Service Bean initialized");
    }

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findByStatus(SchoolConstants.OPEN);
    }

    public void saveMessageDetails(Contact contact) {
        contact.setStatus(SchoolConstants.OPEN);
        contactRepository.save(contact);
    }

    public void updateMsgStatus(int id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty()) return;

        Contact contact1 = contact.get();
        contact1.setStatus(SchoolConstants.CLOSE);
        contactRepository.save(contact1);
    }
}
package com.app.school.service;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Contact;
import com.app.school.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*@RequestScope // every request will have a new instance of this bean
@SessionScope // Same bean instance will be shared across all the requests in the same session
@ApplicationScope // Same bean instance will be shared across all the requests*/
@Service
public class ContactService {

/*    @Setter
    @Getter
    private int counter = 0;*/

    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        log.info("Contact Service Bean initialized");
    }

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findMsgsWithStatus(SchoolConstants.OPEN);
    }

    public boolean saveMessageDetails(Contact contact) {
        contact.setStatus(SchoolConstants.OPEN);
        contact.setCreatedBy(SchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int result = contactRepository.saveContactMsg(contact);
        return result > 0;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy) {
        int result = contactRepository.updateMgStatus(contactId, SchoolConstants.CLOSE, updatedBy);
        return result > 0;
    }
}
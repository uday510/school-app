package com.app.school.service;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Contact;
import com.app.school.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        log.info("Contact Service Bean initialized");
    }

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String  sortField, String sortDir) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1 , pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        return  contactRepository.findOpenMsgs(SchoolConstants.OPEN, pageable);
    }

    public void saveMessageDetails(Contact contact) {
        contact.setStatus(SchoolConstants.OPEN);
        contactRepository.save(contact);
    }

    public void updateMsgStatus(int id) {
        contactRepository.updateMsgStatus(SchoolConstants.CLOSE, id);
    }
}
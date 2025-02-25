package com.app.school.service;

import com.app.school.model.Contact;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

//@RequestScope // every request will have a new instance of this bean
//@SessionScope // Same bean instance will be shared across all the requests in the same session
//@ApplicationScope // Same bean instance will be shared across all the requests
@Service
public class ContactService {

//    @Setter
//    @Getter
//    private int counter = 0;

    public ContactService() {
        log.info("Contact Service Bean initialized");
    }

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = true;
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }

}
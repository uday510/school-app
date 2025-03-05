package com.app.school.rest;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Contact;
import com.app.school.model.Response;
import com.app.school.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    Logger log = LoggerFactory.getLogger(ContactRestController.class);

    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatu(@RequestParam(name = "status") String status) {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact) {
        if (contact == null || contact.getStatus() == null) return List.of();
        return contactRepository.findByStatus(contact.getStatus());
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom, @Valid @RequestBody Contact contact) {

        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message saved successfully");

        return ResponseEntity.status(HttpStatus.CREATED).header("isMsgSaved", "true").body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();

        headers.forEach((key, value) -> {
            log.info(String.format("Header %s = %s", key, value));
        });

        Contact contact = requestEntity.getBody();
        if (contact == null) {
            return  ResponseEntity.notFound().build();
        }

        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactRequest) {
        Response response = new Response();
        Optional<Contact> contact =  contactRepository.findById(contactRequest.getContactId());

        if (contact.isEmpty()) {
            response.setStatusCode("404");
            response.setStatusMessage("Contact not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        contact.get().setStatus(SchoolConstants.CLOSE);
        contactRepository.save(contact.get());

        response.setStatusCode("200");
        response.setStatusMessage("Message closed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

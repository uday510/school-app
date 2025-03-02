package com.app.school.service;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Person;
import com.app.school.model.Roles;
import com.app.school.repository.PersonRepository;
import com.app.school.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {

        Roles role = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.save(person);

        return person.getPersonId() != 0;
    }

}

package com.app.school.service;

import com.app.school.constants.SchoolConstants;
import com.app.school.model.Person;
import com.app.school.model.Roles;
import com.app.school.repository.PersonRepository;
import com.app.school.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public boolean createNewPerson(Person person) {

        Roles role = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person = personRepository.save(person);

        return person.getPersonId() != 0;
    }
}

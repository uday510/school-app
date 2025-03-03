package com.app.school.repository;

import com.app.school.model.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @EntityGraph(attributePaths = {"courses", "roles", "classroom"})
    Person readByEmail(String email);

    @Query("SELECT p FROM Person p " +
            "LEFT JOIN FETCH p.courses " +
            "LEFT JOIN FETCH p.roles " +
            "LEFT JOIN FETCH p.classroom " +
            "WHERE p.email = :email")
    Person findPersonWithAllDetailsByEmail(String email);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.courses WHERE p.personId = :id")
    Optional<Person> findWithCoursesById(@Param("id") Integer id);
}

package com.app.school.repository;

import com.app.school.model.Courses;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    @EntityGraph(attributePaths = {"persons"})
    List<Courses> findAll();

    @EntityGraph(attributePaths = {"persons"})
    Optional<Courses> findById(Integer id);


    @EntityGraph(attributePaths = {"persons"})
    @Query("SELECT c FROM Courses c WHERE c.courseId = :id")
    Optional<Courses> findByIdWithPersons(@Param("id") Integer id);
}

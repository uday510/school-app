package com.app.school.repository;

import com.app.school.model.Holiday;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface HolidayRepository extends CrudRepository<Holiday, String> { }

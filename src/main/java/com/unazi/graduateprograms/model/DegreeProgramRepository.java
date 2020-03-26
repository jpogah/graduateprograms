package com.unazi.graduateprograms.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;



public interface DegreeProgramRepository extends PagingAndSortingRepository<DegreeProgram, Long> {


    @Query("SELECT c from DegreeProgram c where LOWER(c.programName) like :searchTerm%" +
            " or LOWER(c.schoolName) like :searchTerm% or LOWER(c.state) like :location% "
            + " or LOWER(c.city) like :location%")
    @RestResource(path = "searchBy", rel = "searchBy")
    public Page<List<DegreeProgram>> searchBy(@Param("searchTerm") String searchTerm, @Param("location") String location, Pageable p );

}
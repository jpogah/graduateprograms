package com.unazi.graduateprograms.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@RequiredArgsConstructor
@Entity
public class DegreeProgram {


    private @Id
    @GeneratedValue
    Long id;


    private String programName;

    @Column(length = 100000)
    private String programDetails;
    private String website;
    private String email;
    private String tuitionCost;
    private String img;
    private String greRequired;
    private String toeflRequired;
    private String city;
    private String state;
    private String zip;
    private String schoolName;

}
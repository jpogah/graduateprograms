package com.unazi.graduateprograms.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@RequiredArgsConstructor
@Entity
@Table(name="course" , uniqueConstraints={
        @UniqueConstraint(columnNames = {"programName", "schoolName"})
})
public class Course {


    private @Id
    @GeneratedValue
            @Column(updatable = false, insertable = false)
    Long id;


    private String programName;

    @Column(length = 100000)
    private String[] programDetails;
    private String website;
    private String email;

    @Column(length = 100000)
    private String tuitionCost;
    private String img;
    private String greRequired;
    private String toeflRequired;
    private String city;
    private String state;
    private String zip;
    private String schoolName;
    private Long rating;
    private Long totalReviews;
    private String programContact;
    private String applyLink;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;


}
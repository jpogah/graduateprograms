package com.unazi.graduateprograms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private  User user;
    private String username;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    private  Long rating;
    private  String reviewText;
    private Date createdTime;

    public Review(){}

    public Review(Long rating, String reviewText, String username) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.username = username;
    }

    @PrePersist
    public void beforeSave(){
        this.createdTime = new Date();
    }

}

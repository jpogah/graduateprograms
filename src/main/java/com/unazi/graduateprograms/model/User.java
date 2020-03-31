package com.unazi.graduateprograms.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.nio.MappedByteBuffer;
import java.util.List;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private  String name;
    private  String email;
    private  String password;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    private  String userName;

    public User(String name, String email, String password, String userName) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User() {

    }

        @PrePersist
    public void beforeSave(){
        this.password = (new BCryptPasswordEncoder()).encode(this.password);
        this.email = this.email.toLowerCase();

    }

}

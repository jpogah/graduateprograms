package graduatedegrees.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@RequiredArgsConstructor
@Entity
public class Course {


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

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;

}
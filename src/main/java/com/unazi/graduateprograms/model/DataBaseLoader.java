package com.unazi.graduateprograms.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataBaseLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public DataBaseLoader(CourseRepository courseRepository, UserRepository userRepository, ReviewRepository reviewRepository) {

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub

        userRepository.save(new User("admin", "admin@mail.com", "password","admin"));

        GeorgiaTechStrategy st = new GeorgiaTechStrategy("https://www.gatech.edu/","http://www.gradadmiss.gatech.edu/programs-a-z");
        WebScraper ws = new WebScraper();
       reviewRepository.save(new Review(3l , "I really enjoyed the program it was great"));
        reviewRepository.save(new Review(2l , "Not my kind of program"));
        List<Course> degrees = ws.getDegreePrograms(st);
        this.courseRepository.saveAll(degrees);


    }

}
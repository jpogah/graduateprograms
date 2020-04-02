package com.unazi.graduateprograms.model;

import java.util.List;

import com.unazi.graduateprograms.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataBaseLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CourseService courseService;

    @Autowired
    public DataBaseLoader(CourseRepository courseRepository, UserRepository userRepository, ReviewRepository reviewRepository, CourseService courseService) {

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub

        userRepository.save(new User("admin", "admin@mail.com", "password","admin"));

        GeorgiaTechStrategy st = new GeorgiaTechStrategy("https://www.gatech.edu/","http://www.gradadmiss.gatech.edu/programs-a-z");
        WebScraper ws = new WebScraper();
         List<Course> degrees = ws.getDegreePrograms(st);
        this.courseService.saveCourse(degrees);
        reviewRepository.save(new Review(3l , "I really enjoyed the program it was great", "admin"));
        reviewRepository.save(new Review(2l , "Not my kind of program", "admin"));


    }

}
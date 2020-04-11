package com.unazi.graduateprograms;

import java.util.List;

import com.unazi.graduateprograms.otheruniversities.AbileneStrategy;
import com.unazi.graduateprograms.otheruniversities.AlaskaStrategy;
import com.unazi.graduateprograms.services.CourseService;
import com.unazi.graduateprograms.top100universities.strategy.HarvardStrategy;
import com.unazi.graduateprograms.top100universities.strategy.MitStrategy;
import com.unazi.graduateprograms.top100universities.strategy.PrincetonStrategy;
import com.unazi.graduateprograms.top100universities.strategy.StanfordStrategy;
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
        MitStrategy mit = new MitStrategy();
        StanfordStrategy stanfordStrategy = new StanfordStrategy();
        PrincetonStrategy princetonStrategy = new PrincetonStrategy();
        AlaskaStrategy alaskaStrategy = new AlaskaStrategy();
        AbileneStrategy abileneStrategy = new AbileneStrategy();
        HarvardStrategy harvardStrategy = new HarvardStrategy();
        WebScraper ws = new WebScraper();
         List<Course> degrees = ws.getDegreePrograms(st);
        List<Course> mitCourses = ws.getDegreePrograms(mit);
        List<Course> stanfordCourses = ws.getDegreePrograms(stanfordStrategy);
        List<Course> princetonCourses = ws.getDegreePrograms(princetonStrategy);
        List<Course> alaskaCourse = ws.getDegreePrograms(alaskaStrategy);
        List<Course> abileneCourse = ws.getDegreePrograms(abileneStrategy);
        List<Course> harvardCourse = ws.getDegreePrograms(harvardStrategy);
        this.courseService.saveCourse(stanfordCourses);
        this.courseService.saveCourse(mitCourses);
        this.courseService.saveCourse(degrees);
        this.courseService.saveCourse(princetonCourses);
        this.courseService.saveCourse(alaskaCourse);
        this.courseService.saveCourse(abileneCourse);
        this.courseService.saveCourse(harvardCourse);

        reviewRepository.save(new Review(3l , "I really enjoyed the program it was great", "admin"));
        reviewRepository.save(new Review(2l , "Not my kind of program", "admin"));


    }

}
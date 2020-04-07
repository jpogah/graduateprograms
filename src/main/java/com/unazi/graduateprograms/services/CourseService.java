package com.unazi.graduateprograms.services;

import com.unazi.graduateprograms.Course;
import com.unazi.graduateprograms.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void saveCourse(List<Course> courses){
        for (Course c : courses){
            Course oldCourse = courseRepository.findByProgramNameandAndSchoolName(c.getProgramName(), c.getSchoolName());
            if ( null == oldCourse){
                courseRepository.save(c);
            } else {
                c.setId(oldCourse.getId());
                courseRepository.save(c);
            }
        }

    }

}

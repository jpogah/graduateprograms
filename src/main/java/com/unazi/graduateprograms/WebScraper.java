package com.unazi.graduateprograms;

import java.util.List;

public class WebScraper {
    public List<Course> getDegreePrograms(CourseStrategy strategy){

        return strategy.degreeProgram();

    }

}

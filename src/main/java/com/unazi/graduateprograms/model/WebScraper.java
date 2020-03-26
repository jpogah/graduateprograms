package com.unazi.graduateprograms.model;

import java.util.List;

public class WebScraper {
    public List<DegreeProgram> getDegreePrograms(DegreeProgramStrategy strategy){

        return strategy.degreeProgram();

    }

}

package com.unazi.graduateprograms.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataBaseLoader implements CommandLineRunner {

    private final DegreeProgramRepository degreeProgramRepository;

    @Autowired
    public DataBaseLoader(DegreeProgramRepository degreeProgramRepository) {

        this.degreeProgramRepository = degreeProgramRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub



        GeorgiaTechStrategy st = new GeorgiaTechStrategy("https://www.gatech.edu/","http://www.gradadmiss.gatech.edu/programs-a-z");
        WebScraper ws = new WebScraper();
        List<DegreeProgram> degrees = ws.getDegreePrograms(st);
        this.degreeProgramRepository.saveAll(degrees);


    }

}
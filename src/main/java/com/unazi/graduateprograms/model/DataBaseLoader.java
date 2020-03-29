package com.unazi.graduateprograms.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataBaseLoader implements CommandLineRunner {

    private final DegreeProgramRepository degreeProgramRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataBaseLoader(DegreeProgramRepository degreeProgramRepository, UserRepository userRepository) {

        this.degreeProgramRepository = degreeProgramRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub

        userRepository.save(new User("admin", "admin@mail.com", "password","admin"));

        GeorgiaTechStrategy st = new GeorgiaTechStrategy("https://www.gatech.edu/","http://www.gradadmiss.gatech.edu/programs-a-z");
        WebScraper ws = new WebScraper();
        List<DegreeProgram> degrees = ws.getDegreePrograms(st);
        this.degreeProgramRepository.saveAll(degrees);


    }

}
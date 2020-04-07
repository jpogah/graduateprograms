package com.unazi.graduateprograms.config;

import com.unazi.graduateprograms.Course;
import com.unazi.graduateprograms.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Course.class);
        config.exposeIdsFor(User.class);


    }
}
package com.unazi.graduateprograms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    @Autowired
   private  CourseDetailService courseDetailService;

    @GetMapping("/api/course/rating")
    public @ResponseBody  Long  getCourseRating(@RequestParam(name="id") String id){
        return courseDetailService.getCourseRating(Long.parseLong(id));
    }

}

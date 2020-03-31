package com.unazi.graduateprograms;

import com.unazi.graduateprograms.model.Review;
import com.unazi.graduateprograms.model.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDetailService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Long getCourseRating(Long id){
        List<Review> reviews = reviewRepository.findByCourse_Id(id);
        if ( reviews.isEmpty()) return null;
        Long sum = 0l;
        for (Review review: reviews){
            sum += review.getRating();
        }
        return (sum / reviews.size());
    }
}

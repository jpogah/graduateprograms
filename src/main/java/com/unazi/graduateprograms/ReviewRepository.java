package com.unazi.graduateprograms;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    List<Review> findByCourse_Id(Long id);
}

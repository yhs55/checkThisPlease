package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewManageRepository extends JpaRepository<Review,Integer> {

    List<Review> getReviewByDeleteStatus(Boolean deleteStatus);
}

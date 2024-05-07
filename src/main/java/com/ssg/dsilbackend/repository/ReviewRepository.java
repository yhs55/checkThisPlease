package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countByReservationRestaurantId(Long restaurantId);
    @Query("SELECT AVG(r.score) FROM Review r WHERE r.reservation.restaurant.id = :restaurantId")
    Double findAverageScoreByRestaurantId(@Param("restaurantId") Long restaurantId);
}



package com.ssg.dsilbackend.repository;



import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByMemberId(Long memberId);
    Restaurant getRestaurantById(Long id);
    Restaurant findRestaurantByName(String restaurantName);
    void removeByName(String restaurantName);
}


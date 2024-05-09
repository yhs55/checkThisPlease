package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RestaurantManageRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> getRestaurantsByMemberId(Long id);
}

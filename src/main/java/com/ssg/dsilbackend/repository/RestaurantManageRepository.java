package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RestaurantManageRepository extends JpaRepository<Restaurant, Integer> {

}

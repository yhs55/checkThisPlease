package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Restaurant;

public interface RestaurantManage {
    Restaurant getRestaurant(Long id);
    Restaurant updateRestaurant(Long id, Restaurant restaurant);
    void deleteRestaurant(Long id);
}

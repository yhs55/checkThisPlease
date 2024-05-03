package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.repository.RestaurantManageReprository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantManageImpl implements RestaurantManage {

    private final RestaurantManageReprository restaurantManageReprository;

    @Autowired
    public RestaurantManageImpl(RestaurantManageReprository restaurantManageReprository) {
        this.restaurantManageReprository = restaurantManageReprository;
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantManageReprository.findById(id).orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant updateRestaurant) {
        return restaurantManageReprository.findById(id)
                .map(restaurant -> {
                    restaurant.setTel(updateRestaurant.getTel());
                    restaurant.setCrowd(updateRestaurant.getCrowd());
                    restaurant.setImg(updateRestaurant.getImg());
                    restaurant.setDeposit(updateRestaurant.getDeposit());
                    restaurant.setTableCount(updateRestaurant.getTableCount());
                    return restaurantManageReprository.save(restaurant);
                }).orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
    }

    @Override
    public void deleteRestaurant(Long id) {
    restaurantManageReprository.deleteById(id);
    }
}

package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.dto.restaurantManage.RestaurantManageDTO;
import com.ssg.dsilbackend.repository.*;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantManageServiceServiceTest {

    @Autowired
    private RestaurantManageService restaurantManageService;
    private final ModelMapper modelMapper = new ModelMapper();
    private RestaurantManageRepository restaurantManageRepository;

    @Test
    void testGetRestaurant(){
        Long restaurantId = 1L;
        Restaurant restaurant = Restaurant.builder()
                .id(restaurantId)
                .name("테스트 식당")
                .address("테스트시 테스트동")
                .tel("123-456-7890")
                .build();


        when(restaurantManageRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // When
        RestaurantManageDTO restaurantDTO = restaurantManageService.getRestaurant(restaurantId);

        // Then
        assertEquals(restaurantId, restaurantDTO.getId());
        // 기타 필요한 테스트
    }


}

package com.ssg.dsilbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

class RestaurantManageServiceTest {

    @InjectMocks
    private RestaurantManageImpl restaurantManageService;

    @Mock
    private RestaurantManageReprository restaurantManageRepository;

    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReplyRepository replyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetRestaurant() {
        // Given
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("좋은식당");
        restaurant.setId(restaurantId); // 실제 데이터가 채워진 객체 생성
        when(restaurantManageRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // When
        Restaurant retrievedRestaurant = restaurantManageService.getRestaurant(restaurantId);

        // Then
        assertNotNull(retrievedRestaurant);
        assertEquals(restaurantId, retrievedRestaurant.getId());
        System.out.println(retrievedRestaurant);
    }


    @Test
    void testGetRestaurantList() {
        // Given
        Long memberId = 1L;
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        when(restaurantManageRepository.findByMemberId(memberId)).thenReturn(restaurants);

        // When
        List<Restaurant> retrievedRestaurants = restaurantManageService.getRestaurantList(memberId);

        // Then
        assertNotNull(retrievedRestaurants);
        assertEquals(restaurants.size(), retrievedRestaurants.size());
    }

    // Add tests for other methods here
}

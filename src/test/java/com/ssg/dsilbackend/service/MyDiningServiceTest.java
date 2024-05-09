package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.MydiningReviewsDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
@Log4j2
public class MyDiningServiceTest {

    @Autowired
    private MyDiningService myDiningService;
    @Test
    public void testGetMydiningListById() {
        Long memberId = 2L;
        List<MydiningReserveDTO> mydiningReserveDTOList = myDiningService.getMydiningReserveListById(memberId);
        System.out.println(mydiningReserveDTOList);
    }

    @Test
    public void testGetMydiningBookmarksListById(){
        Long memberId = 2L;
        System.out.println(myDiningService.getMydiningBookmarksListById(memberId));
    }

    // 사용자 아이디로 리뷰 리스트 출력
    @Test
    public void testGetMydiningReviewsListById(){
        Long memberId = 2l;
        List<MydiningReviewsDTO> list = myDiningService.getMydiningReviewsListById(memberId);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }

//    @Test
//    public void testGetListRestaurantReview(){
//        Long restaurantId = 1l;
//        List<Review> reviewList = myDiningService.getListRestaurantReview(restaurantId);
//
//        for (int i = 0; i < reviewList.size(); i++) {
//            System.out.println(reviewList.get(i));
//        }
//    }
}

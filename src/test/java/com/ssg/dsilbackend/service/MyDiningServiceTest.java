package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.MydiningReviewsDTO;
import com.ssg.dsilbackend.dto.userManage.RegisterMenuDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
@Log4j2
public class MyDiningServiceTest {

    @Autowired
    private MyDiningService myDiningService;
    @Autowired
    private UserManageService userManageService;


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

    @Test
    public void testRegisterRestaurantInfoWithMenu() {
        // 준비
        List<RegisterMenuDTO> menuDTOs = new ArrayList<>();
        menuDTOs.add(RegisterMenuDTO.builder()
                .name("Bibimbap1")
                .price(10000L)
                .img("bibimbap.jpg")
                .menuInfo("Traditional Korean mixed rice dish")
                .subName("Korean")
                .build());
        menuDTOs.add(RegisterMenuDTO.builder()
                .name("Mapo Tofu1")
                .price(12000L)
                .img("mapo_tofu.jpg")
                .menuInfo("Spicy Chinese tofu dish")
                .subName("Chinese")
                .build());
        String[] categories = {"KOREAN", "CHINESE", "JAPANESE"};
        RestaurantRegisterDTO dto = new RestaurantRegisterDTO(
                "test@example.com1", "password123", "John Doe", "1234567890",
                "123 Main St", "12345", "9876543210", "John's Restaurant", "456 Elm St",
                "9876543211", 4L, 1000L, "1111", categories, menuDTOs
        );



        // 동작
        userManageService.registerRestaurantInfo(dto);

        // 검증
        //verify(menuRepository, times(2)).save(any(Menu.class));
    }

}

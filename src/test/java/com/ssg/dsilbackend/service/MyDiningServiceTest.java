package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
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
}

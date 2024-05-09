package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Point;
import com.ssg.dsilbackend.repository.UserManageRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class UserManageServiceTest {
    private UserManageRepository userManageRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserManageServiceTest(UserManageRepository userManageRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userManageRepository = userManageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    public void signUpTest(){
        Point point = new Point().builder()
                .accumulatePoint(0L)
                .currentPoint(0L)
                .build();


        Members data = new Members()
                .builder()
                .email("dvbf@naver.com")
                .password(bCryptPasswordEncoder.encode("1004"))
                .name("윤호상")
                .point(point)
                .tel("010-9677-7048")
                .address("서울시 노원구 중계동")
                .postcode("01709")
                .build();

        userManageRepository.save(data);
    }
}

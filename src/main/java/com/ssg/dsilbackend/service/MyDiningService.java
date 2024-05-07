package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;

import java.util.List;

public interface MyDiningService {
    List<MydiningReserveDTO> getMydiningListById(Long id);

    void registerReview(ReviewRequest reviewRequest);


}

package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;

import java.util.List;

public interface MyDiningService {
    List<MydiningReserveDTO> getMydiningListById(Long id);
}

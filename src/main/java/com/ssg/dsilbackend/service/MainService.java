package com.ssg.dsilbackend.service;
import com.ssg.dsilbackend.dto.main.MainDTO;

import java.util.List;


public interface MainService {
    List<MainDTO> findByCategoryName(String categoryName);
}
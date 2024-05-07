package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Menu;
import com.ssg.dsilbackend.dto.restaurantList.MenuListDTO;
import com.ssg.dsilbackend.dto.restaurantList.RestaurantListDTO;
import com.ssg.dsilbackend.repository.MembersRepository;
import com.ssg.dsilbackend.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public interface RestaurantListService {
    List<MenuListDTO> findMenus(Long id); // 메뉴 리스트 조회
//    List<MenuListDTO> findRestaurantDetail (Long id);
}

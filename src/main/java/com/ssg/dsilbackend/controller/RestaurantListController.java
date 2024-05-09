package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.restaurantList.MenuListDTO;
import com.ssg.dsilbackend.dto.restaurantList.RestaurantListDTO;
import com.ssg.dsilbackend.service.RestaurantListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant/detail")
public class RestaurantListController {
@Autowired
    private RestaurantListService restaurantListService;
@GetMapping("/{id}")
    public ResponseEntity<List<MenuListDTO>> getMenulist(@PathVariable Long id){
    List<MenuListDTO> menulist = restaurantListService.findMenus(id);
    return ResponseEntity.ok(menulist);
}
}

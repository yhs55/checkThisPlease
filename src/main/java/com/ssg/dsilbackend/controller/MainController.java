package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.CategoryName;
import com.ssg.dsilbackend.dto.main.MainDTO;
import com.ssg.dsilbackend.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/restaurants/category")
public class MainController {


    @Autowired
    private MainService mainService;

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<MainDTO>> getRestaurantsByCategory(@PathVariable CategoryName categoryName) {
        List<MainDTO> restaurants = mainService.findByCategoryName(categoryName);
        if (restaurants.isEmpty()) {
            return ResponseEntity.notFound().build(); // 카테고리에 해당하는 식당이 없는 경우 404 Not Found 반환
        }
        return ResponseEntity.ok(restaurants); // 정상적으로 데이터가 있으면 200 OK와 함께 데이터 반환
    }


}
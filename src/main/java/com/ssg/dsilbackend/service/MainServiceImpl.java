package com.ssg.dsilbackend.service;



import com.ssg.dsilbackend.dto.CategoryName;
import com.ssg.dsilbackend.dto.main.MainDTO;
import com.ssg.dsilbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
//@RequiredArgsConstructor
//@Transactional
public class MainServiceImpl implements MainService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MainDTO> findByCategoryName(CategoryName categoryName) {
        return categoryRepository.findByName(categoryName).stream()
                .map(category -> new MainDTO(category.getRestaurant().getName(), category.getName()))
                .collect(Collectors.toList());
    }
}
package com.ssg.dsilbackend.dto.restaurantManage;

import com.ssg.dsilbackend.dto.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private CategoryName name;
    private Long restaurantId;
}
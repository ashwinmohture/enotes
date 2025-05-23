package com.enotes.enotes_api.service;

import com.enotes.enotes_api.dto.CategoryDto;
import com.enotes.enotes_api.dto.CategoryResponse;
import com.enotes.enotes_api.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();
}

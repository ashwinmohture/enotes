package com.enotes.enotes_api.service;

import com.enotes.enotes_api.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}

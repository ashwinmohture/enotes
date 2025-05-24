package com.enotes.enotes_api.serviceIMPL;

import com.enotes.enotes_api.dto.CategoryDto;
import com.enotes.enotes_api.dto.CategoryResponse;
import com.enotes.enotes_api.entity.Category;
import com.enotes.enotes_api.repository.CategoryRepository;
import com.enotes.enotes_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepo;
    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescriptions(categoryDto.getDescriptions());
//        category.setActive(categoryDto.getActive());

        Category category = mapper.map(categoryDto,Category.class);
        category.setDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saveCategory = categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
       List<Category> categories = categoryRepo.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream().map(cat->mapper.map(cat,CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepo.findByIsActiveTrue();
        List<CategoryResponse>categoryResponseList  = categories.stream().map(cat-> mapper.map(cat,CategoryResponse.class)).toList();
        return categoryResponseList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
       Optional<Category>findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
       if(findByCategory.isPresent()){
           Category category =  findByCategory.get();
           return mapper.map(category,CategoryDto.class);
       }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category>findByCategory = categoryRepo.findById(id);
        if(findByCategory.isPresent()){
            Category category =  findByCategory.get();
            category.setDeleted(true);
            categoryRepo.save(category);
            return true;
        }
        return false;
    }
}

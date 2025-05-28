package com.enotes.enotes_api.serviceIMPL;

import com.enotes.enotes_api.dto.CategoryDto;
import com.enotes.enotes_api.dto.CategoryResponse;
import com.enotes.enotes_api.entity.Category;
import com.enotes.enotes_api.exception.ExistDataException;
import com.enotes.enotes_api.exception.ResourceNotFoundException;
import com.enotes.enotes_api.exception.ValidationException;
import com.enotes.enotes_api.repository.CategoryRepository;
import com.enotes.enotes_api.service.CategoryService;
import com.enotes.enotes_api.util.Validation;
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

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescriptions(categoryDto.getDescriptions());
//        category.setActive(categoryDto.getActive());
        // Validation checking
        validation.categoryValidation(categoryDto);
        // check exist name validation
        Boolean exist = categoryRepo.existsByName(categoryDto.getName().trim());
        if (exist){
            throw new ExistDataException("Category Already Exists");
        }
        Category category = mapper.map(categoryDto,Category.class);
        // Ensure isDeleted and created is never null
        if (category.getDeleted() == null && category.getCreatedBy() == null) {
            category.setDeleted(false);
            category.setCreatedBy(1);
        }
        if(ObjectUtils.isEmpty(category.getId())){
            category.setCreatedOn(new Date());
        }else{
            updateCategory(category);
        }

        Category saveCategory = categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {
       Optional<Category> findById = categoryRepo.findById(category.getId());
       if (findById.isPresent()){
          Category existCategory =  findById.get();
          category.setCreatedBy(existCategory.getCreatedBy());
          category.setCreatedOn(existCategory.getCreatedOn());
          category.setDeleted(existCategory.getDeleted());
          category.setUpdateBy(1);
          category.setUpdatedOn(new Date());
       }
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
    public CategoryDto getCategoryById(Integer id) throws Exception {
//       Optional<Category>findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
       Category category = categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found With ID =" + id));
//       if(findByCategory.isPresent()){
//           Category category =  findByCategory.get();
//           return mapper.map(category,CategoryDto.class);
//       }

        // after exception Handler
        if (!ObjectUtils.isEmpty(category)){
            if (category.getName() == null) {
                throw new IllegalArgumentException("Category name is null");
            }
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

package com.enotes.enotes_api.controller;

import com.enotes.enotes_api.dto.CategoryDto;
import com.enotes.enotes_api.dto.CategoryResponse;
import com.enotes.enotes_api.entity.Category;
import com.enotes.enotes_api.service.CategoryService;
import com.enotes.enotes_api.util.CommonUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if (saveCategory){
           return CommonUtil.createBuildResponseMessage("save success",HttpStatus.CREATED );
//            return new ResponseEntity<>("Save Successfully", HttpStatus.CREATED);
        }else{
            return CommonUtil.createErrorResponse(" category Not Save", HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>("Not Save Successfully", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto>  allCategory = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }else{
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//            return new ResponseEntity<>(allCategory,HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse>  allCategory = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }else{
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//            return new ResponseEntity<>(allCategory,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
       CategoryDto categoryDto = categoryService.getCategoryById(id);
       if(ObjectUtils.isEmpty(categoryDto)){
           return CommonUtil.createErrorResponseMessage("Internal Server Error",HttpStatus.NOT_FOUND);
//           return new ResponseEntity<>("Internal Server Error ",HttpStatus.NOT_FOUND);
       }
       return CommonUtil.createBuildResponse(categoryDto,HttpStatus.OK);
//        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted = categoryService.deleteCategory(id);
        if(deleted){
            return CommonUtil.createBuildResponse("Category Deleted Successfully", HttpStatus.OK);
//            return new ResponseEntity<>("Category Deleted Successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Category Not Deleted Successfully",HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("Category Not Deleted Successfully",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

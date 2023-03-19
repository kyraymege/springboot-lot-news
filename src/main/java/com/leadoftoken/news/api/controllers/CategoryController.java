package com.leadoftoken.news.api.controllers;

import com.leadoftoken.news.business.concretes.CategoryManager;
import com.leadoftoken.news.entities.dtos.CategoryDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@Tag(
        name = "REST APIs for Category Resource"
)
public class CategoryController {
    private CategoryManager categoryManager;


    public CategoryController(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryManager.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") Long category_id){
        return new ResponseEntity<>(categoryManager.getCategory(category_id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryManager.getAllCategories());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable(value = "id") Long categoryId){
        return new ResponseEntity<>(categoryManager.updateCategory(categoryDto,categoryId),HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long categoryId){
        categoryManager.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Removed Successfully!",HttpStatus.OK);
    }

}

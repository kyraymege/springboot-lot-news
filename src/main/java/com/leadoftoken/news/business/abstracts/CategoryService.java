package com.leadoftoken.news.business.abstracts;


import com.leadoftoken.news.entities.concretes.Category;
import com.leadoftoken.news.entities.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
    void deleteCategory(Long categoryId);
}

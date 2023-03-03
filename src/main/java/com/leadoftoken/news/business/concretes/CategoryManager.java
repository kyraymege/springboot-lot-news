package com.leadoftoken.news.business.concretes;

import com.leadoftoken.news.business.abstracts.CategoryService;
import com.leadoftoken.news.entities.concretes.Category;
import com.leadoftoken.news.entities.concretes.Post;
import com.leadoftoken.news.entities.dtos.CategoryDto;
import com.leadoftoken.news.entities.dtos.PostDto;
import com.leadoftoken.news.exceptions.ResourceNotFoundException;
import com.leadoftoken.news.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryManager implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category saved = categoryRepository.save(category);
        return mapToDto(saved);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category)->mapToDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
        categoryRepository.delete(category);
    }
    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}

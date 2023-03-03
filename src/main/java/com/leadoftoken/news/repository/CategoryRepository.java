package com.leadoftoken.news.repository;

import com.leadoftoken.news.entities.concretes.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}

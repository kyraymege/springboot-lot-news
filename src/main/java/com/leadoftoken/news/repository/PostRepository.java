package com.leadoftoken.news.repository;

import com.leadoftoken.news.entities.concretes.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);
}

package com.leadoftoken.news.repository;

import com.leadoftoken.news.entities.concretes.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

    @Query(value = "SELECT * FROM posts WHERE p.title LIKE CONCAT('%', :query, '%')",nativeQuery = true)
    List<Post> searchPostsSQL(String query);
}

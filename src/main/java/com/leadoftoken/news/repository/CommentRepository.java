package com.leadoftoken.news.repository;

import com.leadoftoken.news.entities.concretes.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long id);
}

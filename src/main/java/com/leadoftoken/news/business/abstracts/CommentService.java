package com.leadoftoken.news.business.abstracts;

import com.leadoftoken.news.entities.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long id);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
    void deleteComment(Long postId,Long commentId);
}

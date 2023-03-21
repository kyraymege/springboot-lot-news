package com.leadoftoken.news.api.controllers;

import com.leadoftoken.news.business.concretes.CommentManager;
import com.leadoftoken.news.entities.dtos.CommentDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(
        name = "REST APIs for Comment Resource"
)
public class CommentController {
    private CommentManager commentManager;

    public CommentController(CommentManager commentManager) {
        this.commentManager = commentManager;
    }

    @PostMapping("/{post_id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "post_id") Long post_id,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentManager.createComment(post_id,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}")
    public List<CommentDto> getCommentByPostId(@PathVariable(value = "post_id") Long postId){
        return commentManager.getCommentsByPostId(postId);
    }

    @GetMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "post_id") Long post_id, @PathVariable(value = "comment_id") Long comment_id){
        return new ResponseEntity<>(commentManager.getCommentById(post_id,comment_id), HttpStatus.OK);
    }

    @PutMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id,@Valid @RequestBody CommentDto commentDto ){
        return new ResponseEntity<>(commentManager.updateComment(post_id,comment_id,commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/comment/{comment_id}")
    public  ResponseEntity<String> deleteComment(@PathVariable(value = "post_id") Long post_id, @PathVariable(value = "comment_id") Long comment_id){
        commentManager.deleteComment(post_id,comment_id);
        return new ResponseEntity<>("Comment deleted succesfully!", HttpStatus.OK);
    }
}

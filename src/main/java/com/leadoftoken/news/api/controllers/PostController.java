package com.leadoftoken.news.api.controllers;

import com.leadoftoken.news.business.concretes.PostManager;
import com.leadoftoken.news.entities.dtos.PostDto;
import com.leadoftoken.news.entities.dtos.PostResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostManager postManager;

    public PostController(PostManager postManager) {
        this.postManager = postManager;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postManager.createPost(postDto), HttpStatus.CREATED);
    }

    //Get All Posts
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10" , required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "ascending", required = false) String sortBy){
        return postManager.getAllPosts(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postManager.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") Long id){
        PostDto postResponse = postManager.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postManager.deletePostById(id);
        return new ResponseEntity<>("Post deleted succesfully!", HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(value = "id") Long categoryId){
        List<PostDto> posts = postManager.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPost(@RequestParam("query") String query){
        return ResponseEntity.ok(postManager.searchPost(query));
    }
}

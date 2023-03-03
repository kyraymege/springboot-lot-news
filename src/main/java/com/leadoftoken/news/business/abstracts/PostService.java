package com.leadoftoken.news.business.abstracts;

import com.leadoftoken.news.entities.dtos.PostDto;
import com.leadoftoken.news.entities.dtos.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int page, int pageSize, String sortBy);
    PostDto getPostById(Long id);
    List<PostDto> getPostsByCategory(Long categoryId);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
}

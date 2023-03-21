package com.leadoftoken.news.business.concretes;

import com.leadoftoken.news.business.abstracts.PostService;
import com.leadoftoken.news.entities.concretes.Category;
import com.leadoftoken.news.entities.concretes.Post;
import com.leadoftoken.news.entities.concretes.User;
import com.leadoftoken.news.entities.dtos.PostDto;
import com.leadoftoken.news.entities.dtos.PostResponse;
import com.leadoftoken.news.exceptions.ResourceNotFoundException;
import com.leadoftoken.news.repository.CategoryRepository;
import com.leadoftoken.news.repository.PostRepository;
import com.leadoftoken.news.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostManager implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public PostManager(PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        User user = userRepository.findById(postDto.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User","id",postDto.getUserId()));
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        post.setUser(user);
        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int page, int pageSize, String sortBy) {
        //Pegeable instance
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPage(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        post.setContent(postDto.getContent());
        post.setCategory(category);
        post.setCoverImg(postDto.getCoverImg());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> searchPost(String query) {
        List<Post> searchedPosts = postRepository.searchPosts(query);
        return searchedPosts.stream().map((post) -> mapToDto(post)).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setCoverImg(post.getCoverImg());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCoverImg(postDto.getCoverImg());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUpdatedAt(postDto.getUpdatedAt());
        return post;
    }
}

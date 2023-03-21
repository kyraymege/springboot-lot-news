package com.leadoftoken.news.business.concretes;

import com.leadoftoken.news.business.abstracts.CommentService;
import com.leadoftoken.news.entities.concretes.Comment;
import com.leadoftoken.news.entities.concretes.Post;
import com.leadoftoken.news.entities.concretes.User;
import com.leadoftoken.news.entities.dtos.CommentDto;
import com.leadoftoken.news.exceptions.NewsApiException;
import com.leadoftoken.news.exceptions.ResourceNotFoundException;
import com.leadoftoken.news.repository.CommentRepository;
import com.leadoftoken.news.repository.PostRepository;
import com.leadoftoken.news.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentManager implements CommentService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User","id",commentDto.getUserId()));
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        List<Comment> comments = commentRepository.findByPostId(id);
        return comments.stream().map((comment)->mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //Get Post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //Get Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //Checks if the post has this comment
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        //Get Post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //Get Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //Checks if the post has this comment
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //Update fields
        comment.setContent(commentDto.getContent());

        //save db
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //Get Post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //Get Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //Checks if the post has this comment
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setUpdatedAt(comment.getUpdatedAt());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setUpdatedAt(commentDto.getUpdatedAt());
        return comment;
    }
}

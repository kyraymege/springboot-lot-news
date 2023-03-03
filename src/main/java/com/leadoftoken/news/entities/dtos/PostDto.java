package com.leadoftoken.news.entities.dtos;

import com.leadoftoken.news.entities.concretes.Category;
import com.leadoftoken.news.entities.concretes.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 150, message = "Post title must be at least 3 to 150 characters")
    private String title;
    @NotEmpty
    @Size(min = 3, message = "Post content must be at least 3 characters")
    private String content;
    private String coverImg;

    private Long categoryId;
    private Long userId;
    private Set<CommentDto> comments;
    private Date createdAt;
    private Date updatedAt;
}

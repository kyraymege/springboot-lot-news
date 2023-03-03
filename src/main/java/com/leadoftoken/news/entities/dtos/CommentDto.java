package com.leadoftoken.news.entities.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty
    @Size(min = 3, message = "Post title must be at least 3 characters")
    private String content;
    private Long userId;
    private Date createdAt;
    private Date updatedAt;
}

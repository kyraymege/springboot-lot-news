package com.leadoftoken.news.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int page;
    private int pageSize;
    private long totalElements;
    private int totalPage;
    private boolean last;
}

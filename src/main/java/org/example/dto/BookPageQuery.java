package org.example.dto;

import lombok.Data;

@Data
public class BookPageQuery {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String title;
    private String author;
}
package org.example.dto;

import lombok.Data;

@Data
public class PaperPageReq {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String title;
    private String author;
    private String source;
}

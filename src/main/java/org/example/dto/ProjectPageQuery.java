package org.example.dto;

import lombok.Data;

@Data
public class ProjectPageQuery {

    /** 当前页 */
    private Long pageNum = 1L;

    /** 每页条数 */
    private Long pageSize = 10L;

    /** 项目名称 */
    private String name;

    /** 项目编号 */
    private String code;
}

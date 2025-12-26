package org.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("award")
public class Award {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String targetType;
    private Long targetId;

    private String name;
    private String level;
    private String awardRank;
    private String organization;
    private LocalDate awardDate;
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

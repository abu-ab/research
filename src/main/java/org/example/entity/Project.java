package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class) // JS 安全
    private Long id;

    private String name;        // 项目名称
    private String code;        // 项目编号
    private String type;        // 项目性质
    private String duration;    // 研究周期
    private String leader;      // 项目负责人
    private String description; // 项目简介
}

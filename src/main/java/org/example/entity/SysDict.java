package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@TableName("sys_dict")
@Data
public class SysDict {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String dictType;

    private String dictCode;

    private String dictLabel;

    private Integer sort;

    private Integer status;
}
package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_dict")
@Data
public class SysDict {

    private Long id;

    private String dictType;

    private String dictCode;

    private String dictLabel;

    private Integer sort;

    private Integer status;
}
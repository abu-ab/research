package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName("researcher")
public class Researcher {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String code;
    private String college;
    private String title;
    private String phone;
}

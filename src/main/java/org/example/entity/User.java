package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;
    private String nickname;

    private String role;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

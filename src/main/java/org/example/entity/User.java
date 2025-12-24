package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

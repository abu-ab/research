package org.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Book {
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String name;

    private String author;

    private String publisher;

    private LocalDate publishDate;

    private String isbn;

    private String description;


    @TableField(fill = FieldFill.INSERT)
    private LocalDate createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDate updateTime;
}
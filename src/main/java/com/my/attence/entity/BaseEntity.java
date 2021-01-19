package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
public class BaseEntity implements Serializable {


    @TableField(exist = false)
    private int page = 1;

    @TableField(exist = false)
    private int limit = 10;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private Integer deleted;


    /**
     * 数据权限：用户id
     */
    @TableField(exist = false)
    private List<String> createIds;
}

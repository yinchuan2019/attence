package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
public class BaseEntity {
    @TableField(exist = false)
    private int page = 1;

    @TableField(exist = false)
    private int limit = 10;

    /**
     * 数据权限：用户id
     */
    @TableField(exist = false)
    private List<String> createIds;
}

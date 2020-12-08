package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
public class SysAdminRole extends BaseEntity{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;




}
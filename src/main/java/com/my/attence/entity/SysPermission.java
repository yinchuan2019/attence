package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
public class SysPermission implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "菜单权限名称不能为空")
    private String name;

    private String perms;

    private String url;

    private String icon;

    private String target;

    @NotNull(message = "所属菜单不能为空")
    private Long pid;

    private Integer orderNum;

    @NotNull(message = "菜单权限类型不能为空")
    private Integer type;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField(exist = false)
    private String pidName;
    
}
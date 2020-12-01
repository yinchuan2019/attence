package com.my.attence.modal.Dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.my.attence.entity.BaseEntity;
import com.my.attence.vo.resp.PermissionNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto extends BaseEntity implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    private Integer dataScope;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    @TableField(exist = false)
    private List<PermissionNode> permissionRespNodes;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private List<Long> permissions;


}
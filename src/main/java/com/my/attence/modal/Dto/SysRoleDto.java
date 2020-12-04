package com.my.attence.modal.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.my.attence.entity.BaseEntity;
import com.my.attence.vo.resp.PermissionNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto extends BaseEntity  {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    private Integer dataScope;

    private List<PermissionNode> permissionRespNodes;

    private String startTime;

    private String endTime;

    private List<Long> permissions;


}
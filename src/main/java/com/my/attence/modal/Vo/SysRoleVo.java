package com.my.attence.modal.Vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.my.attence.vo.resp.PermissionNode;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
@Accessors(chain = true)
public class SysRoleVo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String description;

    private Integer status;

    private Integer dataScope;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField(exist = false)
    private List<PermissionNode> permissionRespNodes;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private List<Long> permissions;

    @TableField(exist = false)
    private List<String> depts;

}
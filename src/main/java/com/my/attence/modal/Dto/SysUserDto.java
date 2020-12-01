package com.my.attence.modal.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDto extends BaseEntity implements Serializable {

    private Long id;

    private String username;

    private String salt;

    private String password;

    @TableField(exist = false)
    private String oldPwd;

    @TableField(exist = false)
    private String newPwd;

    private String phone;

    private String deptId;

    private String realName;

    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    private Integer deleted;

    private String createId;

    private String updateId;

    private Integer createWhere;

    private Date createTime;

    private Date updateTime;

    private String startTime;

    private String endTime;

    private List<Long> roleIds;

    private String Captcha;
}
package com.my.attence.modal.request;

import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SysAdminDto extends BaseEntity {

    private Long id;

    private String username;

    private String salt;

    private String password;

    private String oldPwd;

    private String newPwd;

    private String phone;

    private String deptId;

    private String realName;

    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    private String createId;

    private String updateId;

    private Integer createWhere;

    private String startTime;

    private String endTime;

    private List<Long> roleIds;

    private String Captcha;
}
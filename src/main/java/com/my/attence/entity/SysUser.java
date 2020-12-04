package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
public class SysUser extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String salt;

    private String password;

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


}
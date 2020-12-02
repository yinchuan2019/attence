package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author abel
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
public class AttTeacher implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String teaNo;

    private String teaName;

    private String teaEmail;

    private Integer teaAge;

    private Integer teaSex;

    private String teaClass;

    private String teaProfessional;

    private Integer teaStatus;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    private Integer deleted;


}

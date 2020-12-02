package com.my.attence.modal.Dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abel
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AttTeacherDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID=1L;

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

    private Date startTime;

    private Date endTime;




}

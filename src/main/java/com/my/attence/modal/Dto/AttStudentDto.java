package com.my.attence.modal.Dto;

import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * @author abel
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AttStudentDto extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String stuNo;

    private String stuName;

    private String stuEmail;

    private Integer stuAge;

    private Integer stuSex;

    private String stuClass;

    private String stuProfessional;

    private Integer stuStatus;

    private Date startTime;

    private Date endTime;




}

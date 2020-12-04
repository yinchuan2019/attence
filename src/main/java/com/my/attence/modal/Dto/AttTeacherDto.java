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
public class AttTeacherDto extends BaseEntity {

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

    private Date startTime;

    private Date endTime;




}

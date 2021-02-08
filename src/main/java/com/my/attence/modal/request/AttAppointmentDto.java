package com.my.attence.modal.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author abel
 * @since 2020-12-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AttAppointmentDto extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String stuNo;

    private String stuName;

    private String teaNo;

    private String teaName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime endDate;

    private String classRoom;

    private String classType;

    private Integer attType;

    private String remarks;


}

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
public class AttRecordDto extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String teaNo;

    private String teaName;

    private String stuNo;

    private String stuName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime endDate;

    private String workType;

    private Integer attType;

    private String remarks;


}

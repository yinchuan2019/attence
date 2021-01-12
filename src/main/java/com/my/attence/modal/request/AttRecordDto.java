package com.my.attence.modal.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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

    private String attNo;

    private String attName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date attBeginDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date attEndDate;

    private Integer attType;

    private String attRemarks;


}

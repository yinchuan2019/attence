package com.my.attence.modal.Dto;

import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class AttRecordDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String attNo;

    private String attName;

    private LocalDateTime attBeginDate;

    private LocalDateTime attEndDate;

    private Boolean attType;

    private String attRemarks;

    private Boolean deleted;


}

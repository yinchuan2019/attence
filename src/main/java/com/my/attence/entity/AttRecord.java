package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
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
public class AttRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String attNo;

    private String attName;

    private LocalDateTime attBeginDate;

    private LocalDateTime attEndDate;

    private Boolean attType;

    private String attRemarks;

    private Boolean deleted;


    public Integer getId() {
        return id;
    }

    public AttRecord setId(Integer id) {
        this.id = id;
        return this;
    }

}

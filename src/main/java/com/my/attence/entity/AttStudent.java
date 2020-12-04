package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class AttStudent extends BaseEntity {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String stuNo;

    private String stuName;

    private String stuEmail;

    private Integer stuAge;

    private Integer stuSex;

    private String stuClass;

    private String stuProfessional;

    private Integer stuStatus;


}

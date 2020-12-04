package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Data
@Accessors(chain = true)
public class SysRole  extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String description;

    private Integer status;

    private Integer dataScope;


}
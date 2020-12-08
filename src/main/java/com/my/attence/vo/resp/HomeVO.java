package com.my.attence.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * HomeRespVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class HomeVO {
    @ApiModelProperty(value = "用户信息")
    private AdminInfoVO userInfo;
    @ApiModelProperty(value = "目录菜单")
    private List<PermissionNode> menus;

}
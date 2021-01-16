package com.my.attence.controller.admin;

import com.my.attence.common.R;
import com.my.attence.service.RolePermissionService;
import com.my.attence.vo.req.RolePermissionOperationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 角色和菜单关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织管理-角色和菜单关联接口")
public class RolePermissionController {
    @Resource
    private RolePermissionService rolePermissionService;

    @PostMapping("/role/permission")
    @ApiOperation(value = "修改或者新增角色菜单权限接口")
    public R operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo) {
        rolePermissionService.addRolePermission(vo);
        return R.success();
    }
}

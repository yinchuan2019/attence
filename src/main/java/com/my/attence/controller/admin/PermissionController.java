package com.my.attence.controller.admin;


import com.my.attence.common.R;
import com.my.attence.entity.SysPermission;
import com.my.attence.exception.BusinessException;
import com.my.attence.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 菜单权限管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-菜单权限管理")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/permission")
    @ApiOperation(value = "新增菜单权限接口")
    public R addPermission(@RequestBody @Valid SysPermission vo) {
        //verifyFormPid(vo);
        vo.setStatus(1);
        permissionService.save(vo);
        return R.success();
    }

    @DeleteMapping("/permission/{id}")
    @ApiOperation(value = "删除菜单权限接口")
    public R deleted(@PathVariable("id") Long id) {
        permissionService.deleted(id);
        return R.success();
    }

    @PutMapping("/permission")
    @ApiOperation(value = "更新菜单权限接口")
    public R updatePermission(@RequestBody @Valid SysPermission vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return R.fail("id不能为空");
        }
        SysPermission sysPermission = permissionService.getById(vo.getId());
        if (null == sysPermission) {
            throw new BusinessException("传入数据异常");
        }
        // 只有类型变更或者所属菜单变更
        if (sysPermission.getType().equals(vo.getType()) || !sysPermission.getPid().equals(vo.getPid())) {
            //verifyFormPid(vo);
        }
        permissionService.updateById(vo);
        return R.success();
    }

    @GetMapping("/permission/{id}")
    @ApiOperation(value = "查询菜单权限接口")
    public R detailInfo(@PathVariable("id") String id) {
        return R.success(permissionService.getById(id));

    }

    @GetMapping("/permissions")
    @ApiOperation(value = "获取所有菜单权限接口")
    public R getAllMenusPermission() {
        return R.success(permissionService.selectAll());
    }

    @GetMapping("/permission/tree")
    @ApiOperation(value = "获取所有目录菜单树接口")
    public R getAllMenusPermissionTree(@RequestParam(required = false) Long permissionId) {
        return R.success(permissionService.selectAllMenuByTree(permissionId));
    }

    @GetMapping("/permission/tree/all")
    @ApiOperation(value = "获取所有目录菜单树接口")
    public R getAllPermissionTree() {
        return R.success(permissionService.selectAllByTree());
    }

    /**
     * 操作后的菜单类型是目录的时候 父级必须为目录
     * 操作后的菜单类型是菜单的时候，父类必须为目录类型
     * 操作后的菜单类型是按钮的时候 父类必须为菜单类型

    private void verifyFormPid(SysPermission sysPermission) {
        SysPermission parent;
        parent = permissionService.getById(sysPermission.getPid());
        switch (sysPermission.getType()) {
            case 1:
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!"0".equals(sysPermission.getPid())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if (parent == null || parent.getType() != 1) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }

                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getPerms())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            default:
        }
    }
     */
}

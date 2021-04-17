package com.my.attence.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.entity.SysRole;
import com.my.attence.modal.request.SysRoleDto;
import com.my.attence.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;


    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    public R addRole(@RequestBody @Valid SysRoleDto dto) {
        roleService.addRole(dto);
        return R.success();
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    public R deleted(@PathVariable("id") Long id) {
        roleService.deletedRole(id);
        return R.success();
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    public R updateDept(@RequestBody SysRoleDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("RoleController1");
        }
        roleService.updateRole(dto);
        return R.success();
    }

    @PostMapping("/role/bindDept")
    @ApiOperation(value = "绑定角色部门接口")
    public R bindDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return R.fail("RoleController1");
        }
        if (roleService.getById(vo.getId()) == null) {
            return R.fail("RoleController2");
        }

        roleService.updateById(new SysRole().setId(vo.getId()).setDataScope(vo.getDataScope()));
        return R.success();
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    public R detailInfo(@PathVariable("id") Long id) {
        return R.success(roleService.detailInfo(id));
    }

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @SuppressWarnings("unchecked")
    public R pageInfo(@RequestBody SysRoleDto dto) {
        Page page = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getName())) {
            queryWrapper.like(SysRole::getName, dto.getName());
        }
        if (!StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(SysRole::getCreateTime, dto.getStartTime());
        }
        if (!StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(SysRole::getCreateTime, dto.getEndTime());
        }
        if (!StringUtils.isEmpty(dto.getStatus())) {
            queryWrapper.eq(SysRole::getStatus, dto.getStatus());
        }
        queryWrapper.orderByDesc(SysRole::getCreateTime);
        return R.success(roleService.page(page, queryWrapper));
    }

}

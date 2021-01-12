package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.SysRole;
import com.my.attence.entity.SysRolePermission;
import com.my.attence.entity.SysAdminRole;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysRoleMapper;
import com.my.attence.modal.request.SysRoleDto;
import com.my.attence.modal.response.SysRoleVo;
import com.my.attence.service.*;
import com.my.attence.vo.req.RolePermissionOperationReqVO;
import com.my.attence.vo.resp.PermissionNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private HttpSessionService httpSessionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(SysRoleDto dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto,sysRole);
        sysRole.setStatus(1);
        sysRoleMapper.insert(sysRole);
        if (null != dto.getPermissions() && !dto.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(dto.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(SysRoleDto dto) {
        SysRole model = new SysRole();
        BeanUtils.copyProperties(dto,model);
        SysRole sysRole = sysRoleMapper.selectById(model.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", model.getId());
            throw new BusinessException("传入数据异常");
        }
        sysRoleMapper.updateById(model);
        //删除角色权限关联
        rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, sysRole.getId()));
        if (!CollectionUtils.isEmpty(dto.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(dto.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            // 刷新权限
            httpSessionService.refreshRolePermission(sysRole.getId());
        }
    }

    @Override
    public SysRoleVo detailInfo(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole,vo);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException("传入数据异常");
        }
        List<PermissionNode> permissionRespNodes = permissionService.selectAllByTree();
        LambdaQueryWrapper<SysRolePermission> queryWrapper = Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, sysRole.getId());
        Set<Object> checkList = new HashSet<>(rolePermissionService.listObjs(queryWrapper));
        setChecked(permissionRespNodes, checkList);
        vo.setPermissionRespNodes(permissionRespNodes);

        return vo;
    }


    private void setChecked(List<PermissionNode> list, Set<Object> checkList) {
        for (PermissionNode node : list) {
            if (checkList.contains(node.getId())
                    && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setChecked((List<PermissionNode>) node.getChildren(), checkList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(Long id) {
        //删除角色
        sysRoleMapper.deleteById(id);
        //删除角色权限关联
        rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, id));
        //删除角色用户关联
        adminRoleService.remove(Wrappers.<SysAdminRole>lambdaQuery().eq(SysAdminRole::getRoleId, id));
        // 刷新权限
        httpSessionService.refreshRolePermission(id);
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(Long userId) {
        List<Long> roleIds = adminRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<String> getRoleNames(Long userId) {

        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (null == sysRoles || sysRoles.isEmpty()) {
            return null;
        }
        return sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
    }
}

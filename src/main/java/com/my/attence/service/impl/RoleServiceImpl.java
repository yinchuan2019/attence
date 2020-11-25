package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.SysRole;
import com.my.attence.entity.SysRolePermission;
import com.my.attence.entity.SysUserRole;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysRoleMapper;
import com.my.attence.service.*;
import com.my.attence.vo.req.RolePermissionOperationReqVO;
import com.my.attence.vo.resp.DeptRespNodeVO;
import com.my.attence.vo.resp.PermissionRespNode;
import lombok.extern.slf4j.Slf4j;
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
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private HttpSessionService httpSessionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(SysRole vo) {

        vo.setStatus(1);
        sysRoleMapper.insert(vo);
        if (null != vo.getPermissions() && !vo.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(vo.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(SysRole vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException("传入数据异常");
        }
        sysRoleMapper.updateById(vo);
        //删除角色权限关联
        rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, sysRole.getId()));
        if (!CollectionUtils.isEmpty(vo.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            // 刷新权限
            httpSessionService.refreshRolePermission(sysRole.getId());
        }
    }

    @Override
    public SysRole detailInfo(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException("传入数据异常");
        }
        List<PermissionRespNode> permissionRespNodes = permissionService.selectAllByTree();
        LambdaQueryWrapper<SysRolePermission> queryWrapper = Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, sysRole.getId());
        Set<Object> checkList =
                new HashSet<>(rolePermissionService.listObjs(queryWrapper));
        setChecked(permissionRespNodes, checkList);
        sysRole.setPermissionRespNodes(permissionRespNodes);

        return sysRole;
    }

    private void setCheckedDept(List<DeptRespNodeVO> deptRespNodes, Set<Object> checkDeptList) {
        for (DeptRespNodeVO node : deptRespNodes) {
            if (checkDeptList.contains(node.getId())) {
                node.setChecked(true);
            }
            setCheckedDept((List<DeptRespNodeVO>) node.getChildren(), checkDeptList);
        }
    }


    private void setChecked(List<PermissionRespNode> list, Set<Object> checkList) {
        for (PermissionRespNode node : list) {
            if (checkList.contains(node.getId())
                    && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setChecked((List<PermissionRespNode>) node.getChildren(), checkList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        //删除角色
        sysRoleMapper.deleteById(id);
        //删除角色权限关联
        rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, id));
        //删除角色用户关联
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id));
        // 刷新权限
        httpSessionService.refreshRolePermission(id);
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<String> getRoleNames(String userId) {

        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (null == sysRoles || sysRoles.isEmpty()) {
            return null;
        }
        return sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
    }
}

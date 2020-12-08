package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.SysPermission;
import com.my.attence.entity.SysRolePermission;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysPermissionMapper;
import com.my.attence.service.HttpSessionService;
import com.my.attence.service.PermissionService;
import com.my.attence.service.RolePermissionService;
import com.my.attence.service.AdminRoleService;
import com.my.attence.vo.resp.PermissionNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements PermissionService {
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private  SysPermissionMapper sysPermissionMapper;
    @Resource
    private HttpSessionService httpSessionService;

    /**
     * 根据用户查询拥有的权限
     * 先查出用户拥有的角色
     * 再去查用户拥有的权限
     * 也可以多表关联查询
     */
    @Override
    public List<SysPermission> getPermission(Long userId) {
        List<Long> roleIds = adminRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        List<Object> permissionIds = rolePermissionService.listObjs(Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getPermissionId).in(SysRolePermission::getRoleId, roleIds));
        if (permissionIds.isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.<SysPermission>lambdaQuery().in(SysPermission::getId, permissionIds).orderByAsc(SysPermission::getOrderNum);
        return sysPermissionMapper.selectList(queryWrapper);
    }

    /**
     * 删除菜单权限
     * 判断是否 有角色关联
     * 判断是否有子集
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleted(Long permissionId) {
        SysPermission sysPermission = sysPermissionMapper.selectById(permissionId);
        if (null == sysPermission) {
            log.error("传入 的 id:{}不合法", permissionId);
            throw new BusinessException("传入数据异常");
        }
        //获取下一级
        List<SysPermission> childs = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getPid, permissionId));
        if (!childs.isEmpty()) {
            throw new BusinessException(1,"不允许删除");
        }
        sysPermissionMapper.deleteById(permissionId);
        //删除和角色关联
        rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getPermissionId, permissionId));
        //刷新权限
        httpSessionService.refreshPermission(permissionId);
    }


    /**
     * 获取所有菜单权限
     */
    @Override
    public List<SysPermission> selectAll() {
        List<SysPermission> result = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().orderByAsc(SysPermission::getOrderNum));
        if (!result.isEmpty()) {
            for (SysPermission sysPermission : result) {
                SysPermission parent = sysPermissionMapper.selectById(sysPermission.getPid());
                if (parent != null) {
                    sysPermission.setPidName(parent.getName());
                }
            }
        }
        return result;
    }

    /**
     * 获取权限标识
     */
    @Override
    public Set<String> getPermissionsByUserId(Long userId) {

        List<SysPermission> list = getPermission(userId);
        Set<String> permissions = new HashSet<>();
        if (null == list || list.isEmpty()) {
            return null;
        }
        for (SysPermission sysPermission : list) {
            if (!StringUtils.isEmpty(sysPermission.getPerms())) {
                permissions.add(sysPermission.getPerms());
            }

        }
        return permissions;
    }

    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     */
    @Override
    public List<PermissionNode> permissionTreeList(Long userId) {
        List<SysPermission> list = getPermission(userId);
        return getTree(list, false);
    }

    /**
     * 递归获取菜单树
     */
    private List<PermissionNode> getTree(List<SysPermission> all, boolean type) {
        List<PermissionNode> list = new ArrayList<>();
        if (all == null || all.isEmpty()) {
            return list;
        }
        for (SysPermission sysPermission : all) {
            if (0 == sysPermission.getPid()) {
                PermissionNode permissionRespNode = new PermissionNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());

                if (type) {
                    permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                } else {
                    permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                }
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 递归遍历所有
     */
    private List<PermissionNode> getChildAll(Long id, List<SysPermission> all) {
        List<PermissionNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id)) {
                PermissionNode permissionRespNode = new PermissionNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 只递归获取目录和菜单
     */
    private List<PermissionNode> getChildExcBtn(Long id, List<SysPermission> all) {

        List<PermissionNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id) && sysPermission.getType() != 3) {
                PermissionNode permissionRespNode = new PermissionNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 获取所有菜单权限按钮
     */
    @Override
    public List<PermissionNode> selectAllByTree() {
        List<SysPermission> list = selectAll();
        return getTree(list, false);
    }

    /**
     * 获取所有的目录菜单树排除按钮
     * 因为不管是新增或者修改
     * 选择所属菜单目录的时候
     * 都不可能选择到按钮
     * 而且编辑的时候 所属目录不能
     * 选择自己和它的子类
     */
    @Override
    public List<PermissionNode> selectAllMenuByTree(Long permissionId) {

        List<SysPermission> list = selectAll();
        if (!list.isEmpty() && !StringUtils.isEmpty(permissionId)) {
            for (SysPermission sysPermission : list) {
                if (sysPermission.getId().equals(permissionId)) {
                    list.remove(sysPermission);
                    break;
                }
            }
        }
        List<PermissionNode> result = new ArrayList<>();
        //新增顶级目录是为了方便添加一级目录
        PermissionNode respNode = new PermissionNode();
        respNode.setId(0L);
        respNode.setTitle("默认顶级菜单");
        respNode.setSpread(true);
        respNode.setChildren(getTree(list, false));
        result.add(respNode);
        return result;
    }
}

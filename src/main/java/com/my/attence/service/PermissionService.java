package com.my.attence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.SysPermission;
import com.my.attence.vo.resp.PermissionNode;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface PermissionService extends IService<SysPermission> {

    /**
     * 根据userId获取权限
     * @param userId userId
     * @return 权限
     */
    List<SysPermission> getPermission(Long userId);

    /**
     * 删除权限
     * @param permissionId 权限id
     */
    void deleted(Long permissionId);

    /**
     * 获取所有
     * @return List
     */
    List<SysPermission> selectAll();

    /**
     * 根据userId获取权限标志
     * @param userId userId
     * @return Set
     */
    Set<String> getPermissionsByUserId(Long userId);

    /**
     * 根据userId获取权限树
     * @param userId
     * @return List
     */
    List<PermissionNode> permissionTreeList(Long userId);

    /**
     * 根据权限树
     * @return List
     */
    List<PermissionNode> selectAllByTree();

    /**
     * 根据目录树
     * @param permissionId permissionId
     * @return List
     */
    List<PermissionNode> selectAllMenuByTree(Long permissionId);

}

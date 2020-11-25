package com.my.attence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.SysRolePermission;
import com.my.attence.vo.req.RolePermissionOperationReqVO;

/**
 * 角色权限关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RolePermissionService extends IService<SysRolePermission> {

    /**
     * 角色绑定权限
     * @param vo vo
     */
    void addRolePermission(RolePermissionOperationReqVO vo);
}

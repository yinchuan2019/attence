package com.my.attence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.SysRole;
import com.my.attence.modal.Dto.SysRoleDto;
import com.my.attence.modal.Vo.SysRoleVo;

import java.util.List;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
public interface RoleService  extends IService<SysRole> {

    /**
     * 添加角色
     */
    void addRole(SysRoleDto dto);

    /**
     * 更新角色
     */
    void updateRole(SysRoleDto dto);

    /**
     * 根据id获取角色详情
     * @param id id
     * @return SysRoleDto
     */
    SysRoleVo detailInfo(Long id);

    /**
     * 根据id删除
     * @param id id
     */
    void deletedRole(Long id);

    /**
     * 根据userId获取绑定的角色
     * @param userId userId
     * @return List
     */
    List<SysRole> getRoleInfoByUserId(Long userId);

    /**
     * 根据userId获取绑定的角色名
     * @param userId userId
     * @return List
     */
    List<String> getRoleNames(Long userId);
}

package com.my.attence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.SysUser;
import com.my.attence.modal.Dto.SysUserDto;
import com.my.attence.vo.resp.UserOwnRoleRespVO;

/**
 * Created by abel on 2020/11/25
 * TODO
 */
public interface UserService extends IService<SysUser> {

    /**
     * 注册
     */
    void register(SysUserDto dto);

    /**
     * 登陆
     * @return LoginRespVO
     */
    SysUser login(SysUserDto dto);

    /**
     * 更新用户信息
     */
    void updateUserInfo(SysUserDto dto);

    /**
     * 分页
     * @return IPage
     */
    IPage<SysUser> pageInfo(SysUserDto dto);

    /**
     * 添加用户
     */
    void addUser(SysUserDto dto);

    /**
     * 修改密码
     */
    void updatePwd(SysUserDto dto);

    /**
     * 根据userid获取绑定角色
     * @param userId userId
     * @return UserOwnRoleRespVO
     */
    UserOwnRoleRespVO getUserOwnRole(Long userId);

}

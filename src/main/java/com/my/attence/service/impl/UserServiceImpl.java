package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.entity.SysRole;
import com.my.attence.entity.SysUser;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysUserMapper;
import com.my.attence.modal.Dto.SysUserDto;
import com.my.attence.service.PermissionService;
import com.my.attence.service.RoleService;
import com.my.attence.service.UserRoleService;
import com.my.attence.service.UserService;
import com.my.attence.utils.PasswordUtils;
import com.my.attence.vo.req.UserRoleOperationReqVO;
import com.my.attence.vo.resp.UserOwnRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by abel on 2020/11/25
 * TODO
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public void register(SysUserDto dto) {
        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, dto.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户名已存在！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), dto.getSalt());
        sysUser.setPassword(encode);
        sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser login(SysUserDto dto) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, dto.getUsername()));
        if (null == sysUser) {
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysUser.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), dto.getPassword(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        return sysUser;
    }

    @Override
    public void updateUserInfo(SysUserDto dto) {
        SysUser sysUser = sysUserMapper.selectById(dto.getId());
        if (null == sysUser) {
            throw new BusinessException("传入数据异常");
        }

        //如果用户名变更
        if (!sysUser.getUsername().equals(dto.getUsername())) {
            SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, dto.getUsername()));
            if (sysUserOne != null) {
                throw new BusinessException("用户名已存在！");
            }
        }

        if (!StringUtils.isEmpty(dto.getPassword())) {
            String newPassword = PasswordUtils.encode(dto.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword(null);
        }
        sysUserMapper.updateById(sysUser);
    }


    @Override
    public IPage<SysUser> pageInfo(SysUserDto dto) {
        Page page = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getUsername())) {
            queryWrapper.like(SysUser::getUsername, dto.getUsername());
        }
        if (!StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(SysUser::getCreateTime, dto.getStartTime());
        }
        if (!StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(SysUser::getCreateTime, dto.getEndTime());
        }
        if (!StringUtils.isEmpty(dto.getNickName())) {
            queryWrapper.like(SysUser::getNickName, dto.getNickName());
        }
        if (null != dto.getStatus()) {
            queryWrapper.eq(SysUser::getStatus, dto.getStatus());
        }

        IPage<SysUser> iPage = sysUserMapper.selectPage(page, queryWrapper);

        return iPage;
    }

    @Override
    public void addUser(SysUserDto dto) {

        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, dto.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户已存在，请勿重复添加！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), dto.getSalt());
        sysUser.setPassword(encode);
        sysUser.setStatus(1);
        sysUser.setCreateWhere(1);
        sysUserMapper.insert(sysUser);
        if (null != dto.getRoleIds() && !dto.getRoleIds().isEmpty()) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(dto.getId());
            reqVO.setRoleIds(dto.getRoleIds());
            userRoleService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void updatePwd(SysUserDto dto) {

        SysUser sysUser = sysUserMapper.selectById(dto.getId());
        if (sysUser == null) {
            throw new BusinessException("传入数据异常");
        }

        if (!PasswordUtils.matches(sysUser.getSalt(), dto.getOldPwd(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysUser.getPassword().equals(PasswordUtils.encode(dto.getNewPwd(), sysUser.getSalt()))) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        sysUser.setPassword(PasswordUtils.encode(dto.getNewPwd(), sysUser.getSalt()));
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public UserOwnRoleVO getUserOwnRole(Long userId) {
        List<Long> roleIdsByUserId = userRoleService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleService.list();
        UserOwnRoleVO vo = new UserOwnRoleVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        return vo;
    }
}

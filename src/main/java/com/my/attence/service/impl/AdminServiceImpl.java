package com.my.attence.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.entity.SysRole;
import com.my.attence.entity.SysAdmin;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysAdminMapper;
import com.my.attence.modal.request.SysAdminDto;
import com.my.attence.service.PermissionService;
import com.my.attence.service.RoleService;
import com.my.attence.service.AdminRoleService;
import com.my.attence.service.AdminService;
import com.my.attence.utils.PasswordUtils;
import com.my.attence.vo.req.AdminRoleOperationReqVO;
import com.my.attence.vo.resp.AdminOwnRoleVO;
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
public class AdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements AdminService {

    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private AdminRoleService adminRoleService;

    @Override
    public void register(SysAdminDto dto) {
        SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (sysAdminOne != null) {
            throw new BusinessException("用户名已存在！");
        }
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), dto.getSalt());
        sysAdmin.setPassword(encode);
        sysAdminMapper.insert(sysAdmin);
    }

    @Override
    public SysAdmin login(SysAdminDto dto) {
        SysAdmin sysAdmin = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (null == sysAdmin) {
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysAdmin.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        if (!PasswordUtils.matches(sysAdmin.getSalt(), dto.getPassword(), sysAdmin.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        return sysAdmin;
    }

    @Override
    public void updateUserInfo(SysAdminDto dto) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(dto.getId());
        if (null == sysAdmin) {
            throw new BusinessException("传入数据异常");
        }

        //如果用户名变更
        if (!sysAdmin.getUsername().equals(dto.getUsername())) {
            SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
            if (sysAdminOne != null) {
                throw new BusinessException("用户名已存在！");
            }
        }

        if (!StringUtils.isEmpty(dto.getPassword())) {
            String newPassword = PasswordUtils.encode(dto.getPassword(), sysAdmin.getSalt());
            sysAdmin.setPassword(newPassword);
        } else {
            sysAdmin.setPassword(null);
        }
        sysAdminMapper.updateById(sysAdmin);
    }


    @Override
    public IPage<SysAdmin> pageInfo(SysAdminDto dto) {
        Page page = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<SysAdmin> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getUsername())) {
            queryWrapper.like(SysAdmin::getUsername, dto.getUsername());
        }
        if (!StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(SysAdmin::getCreateTime, dto.getStartTime());
        }
        if (!StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(SysAdmin::getCreateTime, dto.getEndTime());
        }
        if (!StringUtils.isEmpty(dto.getNickName())) {
            queryWrapper.like(SysAdmin::getNickName, dto.getNickName());
        }
        if (null != dto.getStatus()) {
            queryWrapper.eq(SysAdmin::getStatus, dto.getStatus());
        }

        IPage<SysAdmin> iPage = sysAdminMapper.selectPage(page, queryWrapper);

        return iPage;
    }

    @Override
    public void addUser(SysAdminDto dto) {

        SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (sysAdminOne != null) {
            throw new BusinessException("用户已存在，请勿重复添加！");
        }
        SysAdmin sysAdmin = new SysAdmin();
        BeanUtil.copyProperties(dto, sysAdmin);
        sysAdmin.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), dto.getSalt());
        sysAdmin.setPassword(encode);
        sysAdmin.setStatus(1);
        sysAdmin.setCreateWhere(1);
        sysAdminMapper.insert(sysAdmin);
        if (null != dto.getRoleIds() && !dto.getRoleIds().isEmpty()) {
            AdminRoleOperationReqVO reqVO = new AdminRoleOperationReqVO();
            reqVO.setUserId(dto.getId());
            reqVO.setRoleIds(dto.getRoleIds());
            adminRoleService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void updatePwd(SysAdminDto dto) {

        SysAdmin sysAdmin = sysAdminMapper.selectById(dto.getId());
        if (sysAdmin == null) {
            throw new BusinessException("传入数据异常");
        }

        if (!PasswordUtils.matches(sysAdmin.getSalt(), dto.getOldPwd(), sysAdmin.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysAdmin.getPassword().equals(PasswordUtils.encode(dto.getNewPwd(), sysAdmin.getSalt()))) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        sysAdmin.setPassword(PasswordUtils.encode(dto.getNewPwd(), sysAdmin.getSalt()));
        sysAdminMapper.updateById(sysAdmin);
    }

    @Override
    public AdminOwnRoleVO getUserOwnRole(Long userId) {
        List<Long> roleIdsByUserId = adminRoleService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleService.list();
        AdminOwnRoleVO vo = new AdminOwnRoleVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        return vo;
    }
}

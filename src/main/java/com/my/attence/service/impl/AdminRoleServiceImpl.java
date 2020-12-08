package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.SysAdminRole;
import com.my.attence.mapper.SysAdminRoleMapper;
import com.my.attence.service.AdminRoleService;
import com.my.attence.vo.req.AdminRoleOperationReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<SysAdminRoleMapper, SysAdminRole> implements AdminRoleService {
    @Resource
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Override
    public List getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<SysAdminRole> queryWrapper = Wrappers.<SysAdminRole>lambdaQuery().select(SysAdminRole::getRoleId).eq(SysAdminRole::getUserId, userId);
        return sysAdminRoleMapper.selectObjs(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(AdminRoleOperationReqVO vo) {
        if (vo.getRoleIds() == null || vo.getRoleIds().isEmpty()) {
            return;
        }
        List<SysAdminRole> list = new ArrayList<>();
        for (Long roleId : vo.getRoleIds()) {
            SysAdminRole sysAdminRole = new SysAdminRole();
            sysAdminRole.setUserId(vo.getUserId());
            sysAdminRole.setRoleId(roleId);
            list.add(sysAdminRole);
        }
        sysAdminRoleMapper.delete(Wrappers.<SysAdminRole>lambdaQuery().eq(SysAdminRole::getUserId, vo.getUserId()));
        //批量插入
        this.saveBatch(list);
    }
}

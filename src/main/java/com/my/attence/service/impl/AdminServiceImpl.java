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

}

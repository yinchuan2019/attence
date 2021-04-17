package com.my.attence.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.constant.Constant;
import com.my.attence.entity.SysAdmin;
import com.my.attence.entity.SysAdminRole;
import com.my.attence.entity.SysRole;
import com.my.attence.exception.BusinessException;
import com.my.attence.mapper.SysAdminMapper;
import com.my.attence.modal.request.SysAdminDto;
import com.my.attence.service.HttpSessionService;
import com.my.attence.service.AdminRoleService;
import com.my.attence.service.AdminService;
import com.my.attence.service.RoleService;
import com.my.attence.utils.PasswordUtils;
import com.my.attence.utils.TaleUtils;
import com.my.attence.vo.req.AdminRoleOperationReqVO;
import com.my.attence.vo.resp.AdminOwnRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by abel on 2020/11/25
 * TODO
 */
@RestController
@Api(tags = "组织模块-用户管理")
@RequestMapping("/sys")
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private HttpSessionService httpSessionService;
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private RoleService roleService;

    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public R login(@RequestBody @Valid SysAdminDto dto,
                   HttpServletRequest request,
                   HttpServletResponse response) {
        //判断验证码
        /*if (!CaptchaUtil.ver(dto.getCaptcha(), request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return DataResult.fail("验证码错误！");
        }*/
        SysAdmin login = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (null == login) {
            return R.fail("该用户不存在,请先注册");
        }
        if (login.getStatus() == 2) {
            return R.fail("该用户已被锁定，请联系运营人员");
        }
        if (!PasswordUtils.matches(login.getSalt(), dto.getPassword(), login.getPassword())) {
            return R.fail("用户名或密码错误");
        }
        request.getSession().setAttribute(Constant.LOGIN_SESSION_ADMIN, login);
        //String token = TaleUtils.getRandomToken() + "#" + login.getId();
        //login.setAccessToken(token);
        return R.success(login);
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public R register(@RequestBody @Valid SysAdminDto dto) {
        SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (sysAdminOne != null) {
            return R.fail("用户名已存在");
        }
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), dto.getSalt());
        sysAdmin.setPassword(encode);
        sysAdminMapper.insert(sysAdmin);
        return R.success();
    }

    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public R unLogin() {
        return R.getResult(1,"AdminController1");
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    public R updateUserInfo(@RequestBody SysAdminDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("AdminController2");
        }
        SysAdmin sysAdmin = sysAdminMapper.selectById(dto.getId());
        if (null == sysAdmin) {
            return R.fail("传入数据异常");
        }

        //如果用户名变更
        if (!sysAdmin.getUsername().equals(dto.getUsername())) {
            SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
            if (sysAdminOne != null) {
                return R.fail("用户名已存在");
            }
        }

        if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getPassword())) {
            String newPassword = PasswordUtils.encode(dto.getPassword(), sysAdmin.getSalt());
            sysAdmin.setPassword(newPassword);
        } else {
            sysAdmin.setPassword(null);
        }
        sysAdminMapper.updateById(sysAdmin);
        return R.success();
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    public R updateUserInfoById(@RequestBody SysAdminDto dto) {
        return R.success();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    public R detailInfo(@PathVariable("id") String id) {
        return R.success(adminService.getById(id));
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    public R youSelfInfo() {
        String userId = "";
        return R.success(adminService.getById(userId));
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    public R pageInfo(@RequestBody SysAdminDto dto) {
        Page page = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<SysAdmin> queryWrapper = Wrappers.lambdaQuery();
        if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getUsername())) {
            queryWrapper.like(SysAdmin::getUsername, dto.getUsername());
        }
        if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(SysAdmin::getCreateTime, dto.getStartTime());
        }
        if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(SysAdmin::getCreateTime, dto.getEndTime());
        }
        if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getNickName())) {
            queryWrapper.like(SysAdmin::getNickName, dto.getNickName());
        }
        if (null != dto.getStatus()) {
            queryWrapper.eq(SysAdmin::getStatus, dto.getStatus());
        }

        IPage<SysAdmin> iPage = sysAdminMapper.selectPage(page, queryWrapper);
        return R.success(iPage);
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    public R addUser(@RequestBody @Valid SysAdminDto dto) {
        SysAdmin sysAdminOne = sysAdminMapper.selectOne(Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, dto.getUsername()));
        if (sysAdminOne != null) {
            return R.fail("用户已存在，请勿重复添加！");
        }
        SysAdmin sysAdmin = new SysAdmin();
        BeanUtil.copyProperties(dto, sysAdmin);
        sysAdmin.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(dto.getPassword(), sysAdmin.getSalt());
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
        return R.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    public R logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.LOGIN_SESSION_ADMIN);
        return R.success();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    public R updatePwd(@RequestBody SysAdminDto dto,
                       HttpServletRequest request) {
        if (StringUtils.isEmpty(dto.getOldPwd()) || StringUtils.isEmpty(dto.getNewPwd())) {
            return R.fail("AdminController3");
        }
        SysAdmin login = TaleUtils.getLoginAdmin(request);

        dto.setId(login.getId());
        dto.setPassword(dto.getNewPwd());

        SysAdmin sysAdmin = sysAdminMapper.selectById(dto.getId());
        if (sysAdmin == null) {
            return R.fail("传入数据异常");
        }

        if (!PasswordUtils.matches(sysAdmin.getSalt(), dto.getOldPwd(), sysAdmin.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysAdmin.getPassword().equals(PasswordUtils.encode(dto.getNewPwd(), sysAdmin.getSalt()))) {
            return R.fail("新密码不能与旧密码相同");
        }
        sysAdmin.setPassword(PasswordUtils.encode(dto.getNewPwd(), sysAdmin.getSalt()));
        sysAdminMapper.updateById(sysAdmin);
        return R.success();
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    public R deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限
        httpSessionService.abortUserByUserIds(userIds);
        LambdaQueryWrapper<SysAdmin> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysAdmin::getId, userIds);
        adminService.remove(queryWrapper);
        return R.success();
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    public R getUserOwnRole(@PathVariable("userId") Long userId) {
        R result = R.success();
        List<Long> roleIdsByUserId = adminRoleService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleService.list();
        AdminOwnRoleVO vo = new AdminOwnRoleVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        result.setData(vo);
        return result;
    }

    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    public R setUserOwnRole(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds) {

        LambdaQueryWrapper<SysAdminRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysAdminRole::getUserId, userId);
        adminRoleService.remove(queryWrapper);
        if (null != roleIds && !roleIds.isEmpty()) {
            AdminRoleOperationReqVO reqVO = new AdminRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            adminRoleService.addUserRoleInfo(reqVO);
        }
        return R.success();
    }
}

package com.my.attence.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.DataResult;
import com.my.attence.constant.Constant;
import com.my.attence.entity.SysAdmin;
import com.my.attence.entity.SysAdminRole;
import com.my.attence.modal.Dto.SysUserDto;
import com.my.attence.service.HttpSessionService;
import com.my.attence.service.AdminRoleService;
import com.my.attence.service.AdminService;
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

    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(@RequestBody @Valid SysUserDto dto,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        //判断验证码
        /*if (!CaptchaUtil.ver(dto.getCaptcha(), request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return DataResult.fail("验证码错误！");
        }*/
        SysAdmin login = adminService.login(dto);
        request.getSession().setAttribute(Constant.LOGIN_SESSION_KEY, login);
        //String token = TaleUtils.getRandomToken() + "#" + login.getId();
        //login.setAccessToken(token);
        return DataResult.success(login);
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult register(@RequestBody @Valid SysUserDto dto) {
        adminService.register(dto);
        return DataResult.success();
    }

    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult unLogin() {
        return DataResult.getResult(1,"请重新登录");
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    public DataResult updateUserInfo(@RequestBody SysUserDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return DataResult.fail("id不能为空");
        }
        adminService.updateUserInfo(dto);
        return DataResult.success();
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    public DataResult updateUserInfoById(@RequestBody SysUserDto dto) {
        return DataResult.success();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(adminService.getById(id));
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    public DataResult youSelfInfo() {
        String userId = "";
        return DataResult.success(adminService.getById(userId));
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    public DataResult pageInfo(@RequestBody SysUserDto dto) {
        return DataResult.success(adminService.pageInfo(dto));
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    public DataResult addUser(@RequestBody @Valid SysUserDto dto) {
        adminService.addUser(dto);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    public DataResult logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.LOGIN_SESSION_KEY);
        return DataResult.success();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    public DataResult updatePwd(@RequestBody SysUserDto dto,
                                HttpServletRequest request) {
        if (StringUtils.isEmpty(dto.getOldPwd()) || StringUtils.isEmpty(dto.getNewPwd())) {
            return DataResult.fail("旧密码与新密码不能为空");
        }
        SysAdmin login = TaleUtils.getLoginUser(request);

        dto.setId(login.getId());
        dto.setPassword(dto.getNewPwd());

        adminService.updatePwd(dto);
        return DataResult.success();
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限
        httpSessionService.abortUserByUserIds(userIds);
        LambdaQueryWrapper<SysAdmin> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysAdmin::getId, userIds);
        adminService.remove(queryWrapper);
        return DataResult.success();
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    public DataResult getUserOwnRole(@PathVariable("userId") Long userId) {
        DataResult result = DataResult.success();
        AdminOwnRoleVO userOwnRole = adminService.getUserOwnRole(userId);
        result.setData(userOwnRole);
        return result;
    }

    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    public DataResult setUserOwnRole(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds) {

        LambdaQueryWrapper<SysAdminRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysAdminRole::getUserId, userId);
        adminRoleService.remove(queryWrapper);
        if (null != roleIds && !roleIds.isEmpty()) {
            AdminRoleOperationReqVO reqVO = new AdminRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            adminRoleService.addUserRoleInfo(reqVO);
        }
        return  DataResult.success();
    }
}

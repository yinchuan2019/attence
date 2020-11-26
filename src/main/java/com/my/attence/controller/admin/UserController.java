package com.my.attence.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.DataResult;
import com.my.attence.constant.Constant;
import com.my.attence.entity.SysUser;
import com.my.attence.entity.SysUserRole;
import com.my.attence.modal.Dto.SysUserDto;
import com.my.attence.service.HttpSessionService;
import com.my.attence.service.UserRoleService;
import com.my.attence.service.UserService;
import com.my.attence.vo.req.UserRoleOperationReqVO;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(@RequestBody @Valid SysUserDto dto,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        //判断验证码
        if (!CaptchaUtil.ver(dto.getCaptcha(), request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return DataResult.fail("验证码错误！");
        }
        SysUser login = userService.login(dto);
        request.getSession().setAttribute(Constant.LOGIN_SESSION_KEY, login);
        //String token = TaleUtils.getRandomToken() + "#" + login.getId();
        //login.setAccessToken(token);
        return DataResult.success(login);
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult register(@RequestBody @Valid SysUserDto dto) {
        userService.register(dto);
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
        userService.updateUserInfo(dto);
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
        return DataResult.success(userService.getById(id));
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    public DataResult youSelfInfo() {
        String userId = "";
        return DataResult.success(userService.getById(userId));
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    public DataResult pageInfo(@RequestBody SysUserDto dto) {
        return DataResult.success(userService.pageInfo(dto));
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    public DataResult addUser(@RequestBody @Valid SysUserDto dto) {
        userService.addUser(dto);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    public DataResult logout() {
        httpSessionService.abortUserByToken();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return DataResult.success();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    public DataResult updatePwd(@RequestBody SysUserDto dto) {
        if (StringUtils.isEmpty(dto.getOldPwd()) || StringUtils.isEmpty(dto.getNewPwd())) {
            return DataResult.fail("旧密码与新密码不能为空");
        }
        Long userId = null;
        dto.setId(userId);
        dto.setPassword(dto.getNewPwd());
        userService.updatePwd(dto);
        return DataResult.success();
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限
        httpSessionService.abortUserByUserIds(userIds);
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysUser::getId, userIds);
        userService.remove(queryWrapper);
        return DataResult.success();
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    public DataResult getUserOwnRole(@PathVariable("userId") Long userId) {
        DataResult result = DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    public DataResult setUserOwnRole(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds) {

        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
        if (null != roleIds && !roleIds.isEmpty()) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            userRoleService.addUserRoleInfo(reqVO);
        }
        //httpSessionService.refreshUerId(userId);
        return  DataResult.success();
    }
}

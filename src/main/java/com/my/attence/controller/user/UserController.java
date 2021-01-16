package com.my.attence.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.constant.Constant;
import com.my.attence.entity.AttStudent;
import com.my.attence.entity.AttTeacher;
import com.my.attence.exception.BusinessException;
import com.my.attence.modal.request.SysAdminDto;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import com.my.attence.utils.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Created by abel on 2021/1/12
 * TODO
 */
@Api(tags = "h5")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private AttStudentService attStudentService;
    @Resource
    private AttTeacherService attTeacherService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口")
    public R login(@RequestBody @Valid SysAdminDto dto,
                   HttpServletRequest request,
                   HttpServletResponse response) {
        if(dto.getUsername().startsWith("T")){
            LambdaQueryWrapper<AttTeacher> eq = Wrappers.<AttTeacher>lambdaQuery().eq(AttTeacher::getTeaId, dto.getUsername());
            AttTeacher one = attTeacherService.getOne(eq);
            if (!PasswordUtils.matchesNoSalt(one.getTeaPwd(), dto.getPassword())) {
                throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute(Constant.LOGIN_SESSION_USER, one);

        }else if(dto.getUsername().startsWith("S")){
            LambdaQueryWrapper<AttStudent> eq = Wrappers.<AttStudent>lambdaQuery().eq(AttStudent::getStuId, dto.getUsername());
            AttStudent one = attStudentService.getOne(eq);
            if (!PasswordUtils.matchesNoSalt(one.getStuPwd(), dto.getPassword())) {
                throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute(Constant.LOGIN_SESSION_USER, one);
        }else {
            return R.fail("用户名不存在");
        }
        return R.success();
    }
}

package com.my.attence.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.constant.Constant;
import com.my.attence.entity.AttRecord;
import com.my.attence.entity.AttStudent;
import com.my.attence.entity.AttTeacher;
import com.my.attence.exception.BusinessException;
import com.my.attence.modal.request.AttRecordDto;
import com.my.attence.modal.request.SysAdminDto;
import com.my.attence.service.AttRecordService;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import com.my.attence.utils.PasswordUtils;
import com.my.attence.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private AttRecordService attRecordService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口")
    public R login(@RequestBody @Valid SysAdminDto dto,HttpServletRequest request) {
        if(dto.getUsername().startsWith("T")){
            LambdaQueryWrapper<AttTeacher> eq = Wrappers.<AttTeacher>lambdaQuery().eq(AttTeacher::getLoginId, dto.getUsername());
            AttTeacher one = attTeacherService.getOne(eq);
            if (!PasswordUtils.matchesNoSalt(one.getTeaPwd(), dto.getPassword())) {
                throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute(Constant.LOGIN_SESSION_USER, one.getLoginId());

        }else if(dto.getUsername().startsWith("S")){
            LambdaQueryWrapper<AttStudent> eq = Wrappers.<AttStudent>lambdaQuery().eq(AttStudent::getLoginId, dto.getUsername());
            AttStudent one = attStudentService.getOne(eq);
            if (!PasswordUtils.matchesNoSalt(one.getStuPwd(), dto.getPassword())) {
                throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute(Constant.LOGIN_SESSION_USER, one.getLoginId());
        }else {
            return R.fail("用户名不存在");
        }
        return R.success();
    }


    @PostMapping(value = "/record")
    public R record(@RequestBody @Valid AttRecordDto dto,HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        AttRecord record = new AttRecord();
        BeanUtils.copyProperties(dto,record);
        if(loginId.startsWith("T")){
            record.setAttType(1);
            AttTeacher teacher = attTeacherService.findByLoginId(loginId);
            record.setTeaName(teacher.getTeaNmKanji());
            if(dto.getStuNo() != null){
                AttStudent student = attStudentService.findByLoginId(dto.getStuNo());
                record.setStuName(student.getStuNmKanji());
            }

             attRecordService.save(record);
        }else if(loginId.startsWith("S")){
            record.setAttType(2);
            AttStudent student = attStudentService.findByLoginId(loginId);
            record.setStuName(student.getStuNmKanji());
            attRecordService.save(record);
        }else {
            return R.fail("用户名不存在");
        }



        return R.success();
    }




}

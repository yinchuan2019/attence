package com.my.attence.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.constant.ClassType;
import com.my.attence.constant.Constant;
import com.my.attence.entity.AttAppointment;
import com.my.attence.entity.AttRecord;
import com.my.attence.entity.AttStudent;
import com.my.attence.entity.AttTeacher;
import com.my.attence.exception.BusinessException;
import com.my.attence.modal.request.AttAppointmentDto;
import com.my.attence.modal.request.AttRecordDto;
import com.my.attence.modal.request.SysAdminDto;
import com.my.attence.service.AttAppointmentService;
import com.my.attence.service.AttRecordService;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import com.my.attence.utils.DateUtils;
import com.my.attence.utils.PasswordUtils;
import com.my.attence.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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
    private AttAppointmentService attAppointmentService;
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


    /**
     * Created by abel on 2021/1/22
     * 预约
     */
    @PostMapping(value = "/appointment")
    public R appointment(@RequestBody @Valid AttAppointmentDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        List<AttAppointment> attAppointments = judgeAppointment(dto);
        if(CollectionUtils.isNotEmpty(attAppointments)){
            return R.fail("已经被预约");
        }

        AttAppointment entity = new AttAppointment();
        BeanUtils.copyProperties(dto,entity);
        ClassType classType = ClassType.valueOf(dto.getClassType());
        entity.setClassType(classType.getName());

        if(loginId.startsWith("T")){
            entity.setAttType(1);
            AttTeacher teacher = attTeacherService.findByLoginId(loginId);
            entity.setTeaNo(loginId);
            entity.setTeaName(teacher.getTeaNmKanji());
            /**学生**/
            if(dto.getStuNo() != null){
                AttStudent student = attStudentService.findByLoginId(dto.getStuNo());
                if(student == null){
                    return R.fail("学号不存在");
                }
                entity.setStuName(student.getStuNmKanji());
                entity.setStuNo(student.getLoginId());
            }
            attAppointmentService.save(entity);
        }else if(loginId.startsWith("S")){
            entity.setAttType(2);
            AttStudent student = attStudentService.findByLoginId(loginId);
            entity.setStuName(student.getStuNmKanji());
            entity.setStuNo(loginId);
            attAppointmentService.save(entity);
        }else {
            return R.fail("用户名不存在");
        }
        return R.success(entity);
    }

    /**
     * Created by abel on 2021/1/22
     * 判断该时间段该教室是否被占用
     */
    public List<AttAppointment> judgeAppointment(@RequestBody @Valid AttAppointmentDto dto){

        LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                .eq(AttAppointment::getClassRoom,dto.getClassRoom())
                .ge(AttAppointment::getBeginDate, dto.getBeginDate().plusMinutes(-30))
                .le(AttAppointment::getEndDate,dto.getEndDate().plusMinutes(30));
        List<AttAppointment> list = attAppointmentService.list(eq);

        return list;
    }


    /**
     * Created by abel on 2021/1/22
     * 考勤签到
     */
    @PostMapping(value = "/signIn")
    public R signIn(@RequestBody @Valid AttRecordDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        AttRecord entity = new AttRecord();
        BeanUtils.copyProperties(dto,entity);
        if(loginId.startsWith("T")){
            ClassType classType = ClassType.valueOf(dto.getWorkType());
            entity.setWorkType(classType.getName());
            entity.setBeginDate(LocalDateTime.now());
            AttTeacher teacher = attTeacherService.findByLoginId(loginId);
            entity.setTeaName(teacher.getTeaNmKanji());
            entity.setTeaNo(loginId);
            if(StringUtils.isNotEmpty(dto.getStuNo())){
                AttStudent student = attStudentService.findByLoginId(dto.getStuNo());
                entity.setStuName(student.getStuNmKanji());
            }
            attRecordService.save(entity);
        }else{
            return R.fail("用户名不存在");
        }
        return R.success(entity);
    }

    /**
     * Created by abel on 2021/1/22
     * 考勤签退
     */
    @PostMapping(value = "/signOut")
    public R signOut(@RequestBody @Valid AttRecordDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                 .eq(AttRecord::getWorkType,ClassType.valueOf(dto.getWorkType()).getName())
                 .eq(AttRecord::getTeaNo,loginId).isNull(AttRecord::getEndDate)
                .orderByDesc(AttRecord::getBeginDate);
        List<AttRecord> list = attRecordService.list(eq);
        if(CollectionUtils.isNotEmpty(list)){
            AttTeacher teacher = attTeacherService.findByLoginId(loginId);
            AttRecord attRecord = list.get(0);
            if(dto.getWorkType().equals(ClassType.CLASS_VIP.name())){
                attRecord.setSalary(teacher.getTeaWage());
            }else if(dto.getWorkType().equals(ClassType.WORK.name())){
                attRecord.setSalary(teacher.getTeaOtherWage());
            }
            attRecord.setEndDate(LocalDateTime.now());
            attRecordService.updateById(attRecord);
            return R.success(attRecord);
        }else{
            return R.fail("未存在签到信息");
        }
    }


    /**
     * Created by abel on 2021/1/22
     * 查询预约信息
     */
    @PostMapping(value = "/findAppointment")
    public R findAppointment(@RequestBody @Valid AttAppointmentDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }

        if(dto.getId() != null){
            AttAppointment entity = attAppointmentService.getById(dto.getId());
            return R.success(entity);
        }

        List<AttAppointment> list;
        if(loginId.startsWith("T")){
            LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                    .eq(AttAppointment::getTeaNo, loginId)
                    .ge(AttAppointment::getBeginDate, DateUtils.getTodayBegin(26));

            list = attAppointmentService.list(eq);

        }else if(loginId.startsWith("S")){
            LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                    .eq(AttAppointment::getStuNo, loginId)
                    .ge(AttAppointment::getBeginDate, DateUtils.getTodayBegin(26));

            list = attAppointmentService.list(eq);
        }else {
            return R.fail("用户名不存在");
        }

        return R.success(list);
    }

    /**
     * Created by abel on 2021/1/22
     * 查询考勤信息
     */
    @PostMapping(value = "/findRecord")
    public R findRecord(@RequestBody @Valid AttRecordDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        if(dto.getId() != null){
            AttRecord entity = attRecordService.getById(dto.getId());
            return R.success(entity);
        }

        LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                .eq(AttRecord::getTeaNo, loginId)
                .ge(AttRecord::getBeginDate, DateUtils.getTodayBegin(26));
        List<AttRecord> list = attRecordService.list(eq);
        Map<String, List<AttRecord>> collect = list.stream().collect(Collectors.groupingBy(AttRecord::getWorkType));

        return R.success(collect);
    }

    /**
     * Created by abel on 2021/1/22
     *查看当前时间断老师预约的学生信息
     */
    @PostMapping(value = "/findStuByTea")
    public R findStuByTea(@RequestBody @Valid AttRecordDto dto, HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }

        LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                .eq(AttAppointment::getAttType, 1)
                .eq(AttAppointment::getClassType, dto.getWorkType())
                .ge(AttAppointment::getBeginDate,LocalDateTime.now())
                .orderByDesc(AttAppointment::getBeginDate)
                .last("limit 1");

        AttAppointment one = attAppointmentService.getOne(eq);

        return R.success(one.getStuNo());
    }

    /**
     * Created by abel on 2021/2/8
     * 老师出勤信息确认
     */
    @PostMapping(value = "/record-info")
    public R recordInfo(HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }

        LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                .eq(AttRecord::getTeaNo,loginId)
                .ge(AttRecord::getBeginDate,DateUtils.getTodayBegin(26))
                .isNotNull(AttRecord::getEndDate)
                .orderByDesc(AttRecord::getBeginDate);

        List<AttRecord> list = attRecordService.list(eq);
        Map<String, Map<Integer, Long>> collect = list.stream().filter(e -> e.getEndDate() != null)
                .collect(Collectors.groupingBy(o -> {
                            if(Objects.nonNull(o.getRemarks())){
                                return o.getWorkType() +"-"+ o.getRemarks();
                            }else {
                                return o.getWorkType();
                            }
                        },
                    Collectors.groupingBy(AttRecord::getAttType,
                        Collectors.summingLong(e ->{
                            long l = Duration.between(e.getBeginDate(), e.getEndDate()).toHours();
                            return l;
                        }))));

        return R.success(collect);
    }

    /**
     * Created by abel on 2021/2/8
     * 社内出勤信息
     */
    @PostMapping(value = "/record-type")
    public R recordType(HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }

        LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                .eq(AttRecord::getTeaNo,loginId)
                .eq(AttRecord::getAttType,1)
                .ge(AttRecord::getBeginDate,DateUtils.getTodayBegin(26))
                .isNotNull(AttRecord::getEndDate)
                .orderByDesc(AttRecord::getBeginDate);

        List<AttRecord> list = attRecordService.list(eq);

        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(e -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = df.format(e.getBeginDate());
            return format;
        }, Collectors.counting()));

        return R.success(collect);
    }
}

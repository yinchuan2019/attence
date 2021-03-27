package com.my.attence.controller.user;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.constant.ClassTypeEnum;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
        if(dto.getUsername().startsWith(Constant.START_WITH_T)){
            LambdaQueryWrapper<AttTeacher> eq = Wrappers.<AttTeacher>lambdaQuery().eq(AttTeacher::getLoginId, dto.getUsername());
            AttTeacher one = attTeacherService.getOne(eq);
            if(one == null){
                return R.fail("老师不存在");
            }
            if (!PasswordUtils.matchesNoSalt(one.getTeaPwd(), dto.getPassword())) {
                throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute(Constant.LOGIN_SESSION_USER, one.getLoginId());

        }else if(dto.getUsername().startsWith(Constant.START_WITH_S)){
            LambdaQueryWrapper<AttStudent> eq = Wrappers.<AttStudent>lambdaQuery().eq(AttStudent::getLoginId, dto.getUsername());
            AttStudent one = attStudentService.getOne(eq);
            if(one == null){
                return R.fail("学生不存在");
            }
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
    public R appointment(@RequestBody @Valid List<AttAppointmentDto> list, HttpServletRequest request){
        AttAppointment entity = new AttAppointment();
        for (AttAppointmentDto dto : list) {
            String loginId = TaleUtils.getLoginUser(request);
            if (Strings.isBlank(loginId)) {
                return R.fail("请先登陆");
            }

            BeanUtils.copyProperties(dto, entity);
            ClassTypeEnum classType = ClassTypeEnum.valueOf(dto.getClassType());
            entity.setClassType(classType.getName());
            List<AttAppointment> attAppointments = judgeAppointment(dto,loginId);
            if (CollectionUtils.isNotEmpty(attAppointments)) {
                return R.fail("已经被预约");
            }

            if (loginId.startsWith(Constant.START_WITH_T)) {
                entity.setAttType(1);
                AttTeacher teacher = attTeacherService.findByLoginId(loginId);
                entity.setTeaNo(loginId);
                entity.setTeaName(teacher.getTeaNmKanji());
                /**学生**/
                if (dto.getStuNo() != null) {
                    AttStudent student = attStudentService.findByLoginId(dto.getStuNo());
                    if (student == null) {
                        return R.fail("学号不存在");
                    }
                    if(Integer.parseInt(student.getStuCourse2()) < 1){
                        return R.fail("学生剩余时间");
                    }
                    final Duration between = Duration.between(entity.getBeginDate(), entity.getEndDate());
                    final long l = Integer.parseInt(student.getStuCourse2()) - between.toMinutes();
                    if(l < 0){
                        return R.fail("学生剩余时间不足");
                    }
                    student.setStuCourse2(String.valueOf(l));
                    attStudentService.updateById(student);
                    entity.setStuName(student.getStuNmKanji());
                    entity.setStuNo(student.getLoginId());
                }
                attAppointmentService.save(entity);
            } else if (loginId.startsWith(Constant.START_WITH_S)) {
                entity.setAttType(2);
                AttStudent student = attStudentService.findByLoginId(loginId);
                entity.setStuName(student.getStuNmKanji());
                entity.setStuNo(loginId);
                LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                        .eq(AttAppointment::getBeginDate,entity.getBeginDate())
                        .isNull(AttAppointment::getClassRoom);

                final List<AttAppointment> attAppointmentList = attAppointmentService.list(eq);
                if(attAppointmentList.size() > 19){
                    return R.fail("当前时间人数已满 : "+ entity.getBeginDate().toString());
                }

                attAppointmentService.save(entity);
                if(classType.equals(ClassTypeEnum.CLASS_COURSE0)){
                    int i = Integer.parseInt(student.getStuCourse0()) - 3;
                    student.setStuCourse0(String.valueOf(i));
                    attStudentService.updateById(student);
                }else if(classType.equals(ClassTypeEnum.CLASS_COURSE1)){
                    int i = Integer.parseInt(student.getStuCourse1()) - 3;
                    student.setStuCourse1(String.valueOf(i));
                    attStudentService.updateById(student);
                }
            } else {
                return R.fail("用户名不存在");
            }
        }
        return R.success(entity);
    }

    /**
     * Created by abel on 2021/1/22
     * 判断该时间段该教室是否被占用
     */
    public List<AttAppointment> judgeAppointment(@RequestBody @Valid AttAppointmentDto dto,String loginId){
        final String classType = dto.getClassType();
        List<AttAppointment> list;
        if(classType.equals(ClassTypeEnum.CLASS_COURSE0) || classType.equals(ClassTypeEnum.CLASS_COURSE1)){
            LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                    .eq(AttAppointment::getBeginDate,dto.getBeginDate())
                    .eq(AttAppointment::getClassType,classType)
                    .eq(AttAppointment::getStuNo,loginId);
            list = attAppointmentService.list(eq);

        }else {
            LambdaQueryWrapper<AttAppointment> eq = Wrappers.<AttAppointment>lambdaQuery()
                    .eq(AttAppointment::getClassRoom,dto.getClassRoom())
                    .ge(AttAppointment::getBeginDate, dto.getBeginDate().plusMinutes(-30))
                    .le(AttAppointment::getEndDate,dto.getEndDate().plusMinutes(30));
            list = attAppointmentService.list(eq);
        }
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
        if(loginId.startsWith(Constant.START_WITH_T)){
            LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                    .eq(AttRecord::getTeaNo,loginId)
                    .isNull(AttRecord::getEndDate)
                    .orderByDesc(AttRecord::getBeginDate);

            List<AttRecord> list = attRecordService.list(eq);
            if(CollectionUtils.isNotEmpty(list)){
                return R.fail("请先退勤");
            }
            ClassTypeEnum classType = ClassTypeEnum.valueOf(dto.getWorkType());
            entity.setWorkType(classType.getName());
            entity.setBeginDate(DateUtils.getCompleteTime(LocalDateTime.now()));
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
                 .eq(AttRecord::getWorkType, ClassTypeEnum.valueOf(dto.getWorkType()).getName())
                 .eq(AttRecord::getTeaNo,loginId).isNull(AttRecord::getEndDate)
                .orderByDesc(AttRecord::getBeginDate);
        List<AttRecord> list = attRecordService.list(eq);
        if(CollectionUtils.isNotEmpty(list)){
            AttTeacher teacher = attTeacherService.findByLoginId(loginId);
            AttRecord attRecord = list.get(0);
            attRecord.setEndDate(DateUtils.getCompleteTime(LocalDateTime.now()));
            final long l = Duration.between(attRecord.getBeginDate(), attRecord.getEndDate()).toMinutes();
            final long sum = l / 15;
            if(dto.getWorkType().equals(ClassTypeEnum.CLASS_VIP.name())){
                final long salary1 = sum * Integer.parseInt(teacher.getTeaWage());
                attRecord.setSalary(String.valueOf(salary1));
            }else if(dto.getWorkType().equals(ClassTypeEnum.CLASS_WORK.name())){
                final long salary2 = sum * Integer.parseInt(teacher.getTeaOtherWage());
                attRecord.setSalary(String.valueOf(salary2));
            }
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

        List<String> classTypeList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(dto.getClassTypes())){
            for (String e : dto.getClassTypes()) {
                ClassTypeEnum classTypeEnum = ClassTypeEnum.valueOf(e);
                classTypeList.add(classTypeEnum.getName());
            }
        }

        List<AttAppointment> list;
        LambdaQueryWrapper<AttAppointment> wrapper = Wrappers.<AttAppointment>lambdaQuery();
        if(CollectionUtils.isNotEmpty(classTypeList)){
            wrapper.in(AttAppointment::getClassType,classTypeList);
        }
        wrapper.ge(AttAppointment::getBeginDate, DateUtils.getTodayBegin());

        if(loginId.startsWith(Constant.START_WITH_T)){
            wrapper.eq(AttAppointment::getTeaNo, loginId);

            list = attAppointmentService.list(wrapper);

        }else if(loginId.startsWith(Constant.START_WITH_S)){
            wrapper.eq(AttAppointment::getStuNo, loginId);

            list = attAppointmentService.list(wrapper);
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
                .ge(AttRecord::getBeginDate, DateUtils.getTodayBegin());
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
                .ge(AttRecord::getBeginDate,DateUtils.getTodayBegin())
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
                            long l = Duration.between(e.getBeginDate(), e.getEndDate()).toMinutes();
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
                .ge(AttRecord::getBeginDate,DateUtils.getTodayBegin())
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

    /**
     * Created by abel on 2021/2/18
     * TODO
     */
    @PostMapping(value = "/course")
    public R course(HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }

        HashMap<Object, Object> res = MapUtil.newHashMap(3);
        AttStudent student = attStudentService.findByLoginId(loginId);
        res.put("student",student);

        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusMonths(1);
        List<LocalDate> sa = DateUtils.querySaturday(localDate.getYear(), localDate.getMonth());
        List<LocalDate> su = DateUtils.querySunday(localDate.getYear(), localDate.getMonth());

        List<String> saturday = sa.stream().map(e -> e.toString()).collect(Collectors.toList());
        List<String> sunday = su.stream().map(e -> e.toString()).collect(Collectors.toList());

        res.put("saturday",saturday);
        res.put("sunday",sunday);

        return R.success(res);
    }


    /**
     * Created by abel on 2021/2/18
     * 查询当前时间老师是否出勤
     */
    @PostMapping(value = "/is-teacher")
    public R isTeacher(HttpServletRequest request){
        String loginId = TaleUtils.getLoginUser(request);
        if(Strings.isBlank(loginId)){
            return R.fail("请先登陆");
        }
        LambdaQueryWrapper<AttRecord> eq = Wrappers.<AttRecord>lambdaQuery()
                .eq(AttRecord::getTeaNo,loginId)
                .isNull(AttRecord::getEndDate)
                .orderByDesc(AttRecord::getBeginDate);

        List<AttRecord> list = attRecordService.list(eq);
        if(CollectionUtils.isNotEmpty(list)){
            return R.success(list.get(0));
        }
        return R.fail("");
    }
}

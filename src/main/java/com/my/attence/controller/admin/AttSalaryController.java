package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.my.attence.common.R;
import com.my.attence.constant.ClassTypeEnum;
import com.my.attence.entity.AttRecord;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.request.AttRecordDto;
import com.my.attence.service.AttRecordService;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import com.my.attence.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abel
 * @since 2020-12-03
 */
@RestController
@RequestMapping("/attence")
public class AttSalaryController {
    @Resource
    private AttRecordService attRecordService;
    @Resource
    private AttStudentService attStudentService;
    @Resource
    private AttTeacherService attTeacherService;


    @PostMapping("/salary")
    @ApiOperation(value = "新增接口")
    public R add(@RequestBody @Valid AttRecordDto dto) {
        AttRecord entity = new AttRecord();

        BeanUtil.copyProperties(dto,entity);
        AttTeacher teacher = attTeacherService.findByLoginId(dto.getTeaNo());
        if(teacher == null){
            return R.fail("AttSalaryController1");
        }
        entity.setTeaName(teacher.getTeaNmKanji());
        //entity.setWorkType(ClassTypeEnum.CLASS_ORDER.getName());
        entity.setAttType(1);
        attRecordService.save(entity);
        return R.success();
    }

    @DeleteMapping("/salary")
    @ApiOperation(value = "删除接口")
    public R delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attRecordService.removeById(id);
        }
        return R.success();
    }

    @PutMapping("/salary")
    @ApiOperation(value = "更新信息接口")
    public R update(@RequestBody AttRecordDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("AttSalaryController2");
        }
        AttRecord entity = new AttRecord();
        BeanUtil.copyProperties(dto,entity);
        attRecordService.updateById(entity);
        return R.success();
    }

    @GetMapping("/salary")
    @ApiOperation(value = "查询详情接口")
    public R detailInfo(@RequestBody Long id) {
        AttRecord entity = attRecordService.getById(id);
        return R.success(entity);
    }

    @PostMapping("/salarys")
    @ApiOperation(value = "分页获取信息接口")
    public R salarys(@RequestBody AttRecordDto dto) {
        List<AttRecordDto> attRecordDtos = findsSalarys(dto);
        return R.success(attRecordDtos);
    }

    public List<AttRecordDto> findsSalarys( AttRecordDto dto){
        LambdaQueryWrapper<AttRecord> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getBeginDate())) {
            queryWrapper.gt(AttRecord::getBeginDate, dto.getBeginDate());
        }
        if (!StringUtils.isEmpty(dto.getEndDate())) {
            queryWrapper.lt(AttRecord::getEndDate, dto.getEndDate());
        }
        if (!StringUtils.isEmpty(dto.getTeaNo())) {
            queryWrapper.eq(AttRecord::getTeaNo, dto.getTeaNo());
        }
        if (!StringUtils.isEmpty(dto.getTeaName())) {
            queryWrapper.eq(AttRecord::getTeaName, dto.getTeaName());
        }
        if (!StringUtils.isEmpty(dto.getWorkType())) {
            queryWrapper.eq(AttRecord::getWorkType, ClassTypeEnum.valueOf(dto.getWorkType()).getName());
        }
        queryWrapper.orderByDesc(AttRecord::getBeginDate);
        List<AttRecord> list = attRecordService.list(queryWrapper);
        //讲师姓名 - 工作类型 - 出勤类型 - 总工资
        /*Map<String, Map<String, Map<Integer, Double>>> map = list.stream().filter(e -> e.getEndDate() != null).collect(Collectors.groupingBy(AttRecord::getTeaNo,
                Collectors.groupingBy(AttRecord::getWorkType,
                        Collectors.groupingBy(AttRecord::getAttType,
                                Collectors.summingDouble(e -> {
                                    if(e.getWorkType().equals(ClassTypeEnum.CLASS_OTHER.getName()) ){
                                        return Double.parseDouble(e.getSalary());
                                    }else{
                                        long l = Duration.between(e.getBeginDate(), e.getEndDate()).toMinutes();
                                        return Double.parseDouble(e.getSalary());
                                    }}
                                )))));*/
        //时长
        Map<String, Map<String, Map<Integer, List>>> mapp = list.stream().filter(e -> e.getEndDate() != null).collect(Collectors.groupingBy(AttRecord::getTeaNo,
                Collectors.groupingBy(AttRecord::getWorkType,
                        Collectors.groupingBy(AttRecord::getAttType,
                                Collectors.collectingAndThen(Collectors.toList(), m -> {
                                    final long totalCount = m.stream().count();
                                    final List<Object> rlist = Lists.newArrayList();
                                    double sum = 0.0;
                                    for (AttRecord attRecord : m) {
                                        double salary = Double.parseDouble(attRecord.getSalary());
                                        sum += salary;
                                    }

                                    long duration = 0;
                                    for (AttRecord attRecord : m) {
                                        long l = Duration.between(attRecord.getBeginDate(), attRecord.getEndDate()).toMinutes();
                                        duration += l;
                                    }
                                    rlist.add(duration);
                                    rlist.add(sum);
                                    return rlist;
                                })
                        ))));


        List<AttRecordDto> resList = Lists.newArrayList();
        for(Object k : mapp.keySet()) {
            String key1 = (String) k;
            AttTeacher teacher = attTeacherService.findByLoginId(key1);

            Map<String, Map<Integer, List>> map1 = mapp.get(key1);
            for (Object k2 : map1.keySet()) {
                String key2 = (String) k2;
                Map<Integer, List> map2 = map1.get(key2);
                for (Object k3 : map2.keySet()) {
                    Integer key3 = (Integer) k3;
                    List list1 = map2.get(key3);

                    AttRecordDto res = new AttRecordDto();
                    res.setTeaName(teacher.getTeaNmKanji());
                    res.setTeaNo(key1);
                    res.setWorkType(key2);
                    res.setAttType(key3);
                    if(!ObjectUtils.isEmpty(list1.get(0))){
                        res.setDuration(list1.get(0).toString());
                    }
                    if(!ObjectUtils.isEmpty(list1.get(1))) {
                        res.setSalary(list1.get(1).toString());
                    }
                    resList.add(res);
                }
            }
        }
        return resList;
    }
}


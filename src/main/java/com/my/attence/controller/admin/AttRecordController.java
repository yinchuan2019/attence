package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.constant.ClassTypeEnum;
import com.my.attence.entity.AttRecord;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.request.AttRecordDto;
import com.my.attence.service.AttRecordService;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

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
public class AttRecordController {
    @Resource
    private AttRecordService attRecordService;
    @Resource
    private AttStudentService attStudentService;
    @Resource
    private AttTeacherService attTeacherService;


    @PostMapping("/record")
    @ApiOperation(value = "新增接口")
    public R add(@RequestBody @Valid AttRecordDto dto) {
        AttRecord entity = new AttRecord();
        BeanUtil.copyProperties(dto,entity);
        AttTeacher teacher = attTeacherService.findByLoginId(dto.getTeaNo());
        if(Objects.isNull(teacher)){
            return R.fail("save");
        }
        entity.setTeaName(teacher.getTeaNmKanji());
        entity.setWorkType(ClassTypeEnum.valueOf(dto.getWorkType()).getName());

        if(dto.getWorkType().equals(ClassTypeEnum.CLASS_VIP.getName()) ){
            entity.setSalary(teacher.getTeaWage());
        }else if(dto.getWorkType().equals(ClassTypeEnum.CLASS_WORK.getName()) ){
            entity.setTeaName(teacher.getTeaOtherWage());
        }
        attRecordService.save(entity);
        return R.success();
    }

    @DeleteMapping("/record")
    @ApiOperation(value = "删除接口")
    public R delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attRecordService.removeById(id);
        }
        return R.success();
    }

    @PutMapping("/record")
    @ApiOperation(value = "更新信息接口")
    public R update(@RequestBody AttRecordDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("id不能为空");
        }
        AttRecord entity = new AttRecord();
        BeanUtil.copyProperties(dto,entity);
        entity.setWorkType(ClassTypeEnum.valueOf(dto.getWorkType()).getName());
        attRecordService.updateById(entity);
        return R.success();
    }

    @GetMapping("/record")
    @ApiOperation(value = "查询详情接口")
    public R detailInfo(@RequestBody Long id) {
        AttRecord entity = attRecordService.getById(id);
        return R.success(entity);
    }

    @PostMapping("/records")
    @ApiOperation(value = "分页获取信息接口")
    public R pageInfo(@RequestBody AttRecordDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttRecord> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getBeginDate())) {
            queryWrapper.gt(AttRecord::getBeginDate, dto.getBeginDate());
        }
        if (!StringUtils.isEmpty(dto.getEndDate())) {
            queryWrapper.lt(AttRecord::getEndDate, dto.getEndDate());
        }
        if (!StringUtils.isEmpty(dto.getAttType())) {
            queryWrapper.eq(AttRecord::getAttType, dto.getAttType());
        }
        if (!StringUtils.isEmpty(dto.getWorkType())) {
            queryWrapper.eq(AttRecord::getWorkType, ClassTypeEnum.valueOf(dto.getWorkType()).getName());
        }
        queryWrapper.orderByDesc(AttRecord::getBeginDate);
        IPage<AttRecord> page = attRecordService.page(p, queryWrapper);
        for (AttRecord e:page.getRecords()) {
            if(e.getEndDate() != null){
                final Duration between = Duration.between(e.getBeginDate(), e.getEndDate());
                e.setDuration(String.valueOf(between.toHours()));
            }
        }
        return R.success(page);
    }
}


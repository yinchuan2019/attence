package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.entity.AttAppointment;
import com.my.attence.modal.request.AttAppointmentDto;
import com.my.attence.service.AttAppointmentService;
import com.my.attence.service.AttStudentService;
import com.my.attence.service.AttTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
public class AttAppointmentController {
    @Resource
    private AttAppointmentService attAppointmentService;
    @Resource
    private AttStudentService attStudentService;
    @Resource
    private AttTeacherService attTeacherService;


    @PostMapping("/appointment")
    @ApiOperation(value = "新增接口")
    public R add(@RequestBody @Valid AttAppointmentDto dto) {
        AttAppointment entity = new AttAppointment();
        BeanUtil.copyProperties(dto,entity);
        attAppointmentService.save(entity);
        return R.success();
    }

    @DeleteMapping("/appointment")
    @ApiOperation(value = "删除接口")
    public R delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attAppointmentService.removeById(id);
        }
        return R.success();
    }

    @PutMapping("/appointment")
    @ApiOperation(value = "更新信息接口")
    public R update(@RequestBody AttAppointmentDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("id不能为空");
        }
        AttAppointment entity = new AttAppointment();
        BeanUtil.copyProperties(dto,entity);
        attAppointmentService.updateById(entity);
        return R.success();
    }

    @GetMapping("/appointment")
    @ApiOperation(value = "查询详情接口")
    public R detailInfo(@RequestBody Long id) {
        AttAppointment entity = attAppointmentService.getById(id);
        return R.success(entity);
    }

    @PostMapping("/appointments")
    @ApiOperation(value = "分页获取信息接口")
    public R pageInfo(@RequestBody AttAppointmentDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttAppointment> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getStuNo())) {
            queryWrapper.like(AttAppointment::getStuNo, dto.getStuNo());
        }
        if (!StringUtils.isEmpty(dto.getStuName())) {
            queryWrapper.like(AttAppointment::getStuName, dto.getStuName());
        }
        if (!StringUtils.isEmpty(dto.getBeginDate())) {
            queryWrapper.gt(AttAppointment::getBeginDate, dto.getBeginDate());
        }
        if (!StringUtils.isEmpty(dto.getEndDate())) {
            queryWrapper.lt(AttAppointment::getEndDate, dto.getEndDate());
        }
        if (!StringUtils.isEmpty(dto.getAttType())) {
            queryWrapper.eq(AttAppointment::getAttType, dto.getAttType());
        }
        queryWrapper.orderByDesc(AttAppointment::getBeginDate);
        IPage<AttAppointment> page = attAppointmentService.page(p, queryWrapper);
        return R.success(page);
    }
}


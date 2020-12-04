package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.DataResult;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.Dto.AttTeacherDto;
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
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/attence")
public class AttTeacherController {
    @Resource
    private AttTeacherService attTeacherService;

    @PostMapping("/teacher")
    @ApiOperation(value = "新增接口")
    public DataResult add(@RequestBody @Valid AttTeacherDto dto) {
        AttTeacher entity = new AttTeacher();
        BeanUtil.copyProperties(dto,entity);
        //入职
        //entity.setTeaStatus(1);
        attTeacherService.save(entity);
        return DataResult.success();
    }

    @DeleteMapping("/teacher")
    @ApiOperation(value = "删除接口")
    public DataResult delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attTeacherService.removeById(id);
        }
        return DataResult.success();
    }

    @PutMapping("/teacher")
    @ApiOperation(value = "更新信息接口")
    public DataResult update(@RequestBody AttTeacherDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return DataResult.fail("id不能为空");
        }
        AttTeacher entity = new AttTeacher();
        BeanUtil.copyProperties(dto,entity);
        attTeacherService.updateById(entity);
        return DataResult.success();
    }

    @GetMapping("/teacher")
    @ApiOperation(value = "查询详情接口")
    public DataResult detailInfo(@RequestBody Long id) {
        AttTeacher entity = attTeacherService.getById(id);
        return DataResult.success(entity);
    }

    @PostMapping("/teachers")
    @ApiOperation(value = "分页获取信息接口")
    public DataResult pageInfo(@RequestBody AttTeacherDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttTeacher> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getTeaNo())) {
            queryWrapper.like(AttTeacher::getTeaNo, dto.getTeaNo());
        }
        if (!StringUtils.isEmpty(dto.getTeaName())) {
            queryWrapper.like(AttTeacher::getTeaName, dto.getTeaName());
        }
        if (!StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(AttTeacher::getCreateTime, dto.getStartTime());
        }
        if (!StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(AttTeacher::getCreateTime, dto.getEndTime());
        }
        if (!StringUtils.isEmpty(dto.getTeaStatus())) {
            queryWrapper.eq(AttTeacher::getTeaStatus, dto.getTeaStatus());
        }
        queryWrapper.orderByDesc(AttTeacher::getCreateTime);
        IPage page = attTeacherService.page(p, queryWrapper);
        return DataResult.success(page);
    }

}


package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.DataResult;
import com.my.attence.entity.AttStudent;
import com.my.attence.modal.Dto.AttStudentDto;
import com.my.attence.service.AttStudentService;
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
public class AttStudentController {
    @Resource
    private AttStudentService attStudentService;

    @PostMapping("/student")
    @ApiOperation(value = "新增接口")
    public DataResult add(@RequestBody @Valid AttStudentDto dto) {
        AttStudent entity = new AttStudent();
        BeanUtil.copyProperties(dto,entity);
        entity.setStuStatus(1);
        attStudentService.save(entity);
        return DataResult.success();
    }

    @DeleteMapping("/student")
    @ApiOperation(value = "删除接口")
    public DataResult delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attStudentService.removeById(id);
        }
        return DataResult.success();
    }

    @PutMapping("/student")
    @ApiOperation(value = "更新信息接口")
    public DataResult update(@RequestBody AttStudentDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return DataResult.fail("id不能为空");
        }
        AttStudent entity = new AttStudent();
        BeanUtil.copyProperties(dto,entity);
        attStudentService.updateById(entity);
        return DataResult.success();
    }

    @GetMapping("/student")
    @ApiOperation(value = "查询详情接口")
    public DataResult detailInfo(@RequestBody Long id) {
        AttStudent entity = attStudentService.getById(id);
        return DataResult.success(entity);
    }

    @PostMapping("/students")
    @ApiOperation(value = "分页获取信息接口")
    public DataResult pageInfo(@RequestBody AttStudentDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttStudent> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getStuNo())) {
            queryWrapper.like(AttStudent::getStuNo, dto.getStuNo());
        }
        if (!StringUtils.isEmpty(dto.getStuName())) {
            queryWrapper.like(AttStudent::getStuName, dto.getStuName());
        }
        if (!StringUtils.isEmpty(dto.getStartTime())) {
            queryWrapper.gt(AttStudent::getCreateTime, dto.getStartTime());
        }
        if (!StringUtils.isEmpty(dto.getEndTime())) {
            queryWrapper.lt(AttStudent::getCreateTime, dto.getEndTime());
        }
        if (!StringUtils.isEmpty(dto.getStuStatus())) {
            queryWrapper.eq(AttStudent::getStuStatus, dto.getStuStatus());
        }
        queryWrapper.orderByDesc(AttStudent::getCreateTime);
        IPage page = attStudentService.page(p, queryWrapper);
        return DataResult.success(page);
    }

}


package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.request.AttTeacherDto;
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
    public R add(@RequestBody @Valid AttTeacherDto dto) {
        AttTeacher entity = new AttTeacher();
        BeanUtil.copyProperties(dto,entity);
        //入职
        entity.setTeaStatus(1);
        entity.setTeaPwd("111111");
        final AttTeacher teacher = attTeacherService.findByLoginId(dto.getLoginId());
        if(teacher != null){
            return R.fail("用户id已经存在");
        }

        attTeacherService.save(entity);
        return R.success();
    }

    @DeleteMapping("/teacher")
    @ApiOperation(value = "删除接口")
    public R delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attTeacherService.removeById(id);
        }
        return R.success();
    }

    @PutMapping("/teacher")
    @ApiOperation(value = "更新信息接口")
    public R update(@RequestBody AttTeacherDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("id不能为空");
        }
        AttTeacher entity = new AttTeacher();
        BeanUtil.copyProperties(dto,entity);
        attTeacherService.updateById(entity);
        return R.success();
    }

    @GetMapping("/teacher")
    @ApiOperation(value = "查询详情接口")
    public R detailInfo(@RequestBody Long id) {
        AttTeacher entity = attTeacherService.getById(id);
        return R.success(entity);
    }

    @PostMapping("/teachers")
    @ApiOperation(value = "分页获取信息接口")
    public R pageInfo(@RequestBody AttTeacherDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttTeacher> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getLoginId())) {
            queryWrapper.like(AttTeacher::getLoginId, dto.getLoginId());
        }
        if (!StringUtils.isEmpty(dto.getTeaNmKanji())) {
            queryWrapper.like(AttTeacher::getTeaNmKanji, dto.getTeaNmKanji());
        }
        if (!StringUtils.isEmpty(dto.getTeaStatus())) {
            queryWrapper.eq(AttTeacher::getTeaStatus, dto.getTeaStatus());
        }
        queryWrapper.orderByDesc(AttTeacher::getCreateTime);
        IPage page = attTeacherService.page(p, queryWrapper);
        return R.success(page);
    }

}


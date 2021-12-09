package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.R;
import com.my.attence.constant.Constant;
import com.my.attence.entity.AttStudent;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.request.AttStudentDto;
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
    public R add(@RequestBody @Valid AttStudentDto dto) {
        AttStudent entity = new AttStudent();
        BeanUtil.copyProperties(dto,entity);
//        entity.setStuStatus(1);
        //entity.setStuPwd("111111");
        if(! dto.getLoginId().startsWith(Constant.START_WITH_S)){
            return R.fail("fail");
        }
        final AttStudent student = attStudentService.findByLoginId(dto.getLoginId());
        if(student != null){
            return R.fail("AttStudentController1");
        }
        attStudentService.save(entity);
        return R.success();
    }

    @DeleteMapping("/student")
    @ApiOperation(value = "删除接口")
    public R delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attStudentService.removeById(id);
        }
        return R.success();
    }

    @PutMapping("/student")
    @ApiOperation(value = "更新信息接口")
    public R update(@RequestBody AttStudentDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail("AttStudentController2");
        }
        AttStudent entity = new AttStudent();
        BeanUtil.copyProperties(dto,entity);
        attStudentService.updateById(entity);
        return R.success();
    }

    @PutMapping("/student_status")
    @ApiOperation(value = "更新状态接口")
    public R updateStatus(@RequestBody Long id) {
        AttStudent entity = attStudentService.getById(id);
        if (ObjectUtil.isEmpty(entity)) {
            return R.fail("AttStudentController3");
        }
        if(entity.getStuStatus() == 0){
            entity.setStuStatus(1);
        }else{
            entity.setStuStatus(0);
        }
        attStudentService.updateById(entity);
        return R.success();
    }

    @GetMapping("/student")
    @ApiOperation(value = "查询详情接口")
    public R detailInfo(@RequestBody Long id) {
        AttStudent entity = attStudentService.getById(id);
        return R.success(entity);
    }

    @PostMapping("/students")
    @ApiOperation(value = "分页获取信息接口")
    public R pageInfo(@RequestBody AttStudentDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttStudent> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getLoginId())) {
            queryWrapper.like(AttStudent::getLoginId, dto.getLoginId());
        }
        if (!StringUtils.isEmpty(dto.getStuNmKanji())) {
            queryWrapper.like(AttStudent::getStuNmKanji, dto.getStuNmKanji());
        }
        if (!StringUtils.isEmpty(dto.getStuNmRoma())) {
            queryWrapper.like(AttStudent::getStuNmRoma, dto.getStuNmRoma());
        }
        if (!StringUtils.isEmpty(dto.getStuStatus())) {
            queryWrapper.eq(AttStudent::getStuStatus, dto.getStuStatus());
        }
        queryWrapper.orderByDesc(AttStudent::getId);
        IPage page = attStudentService.page(p, queryWrapper);
        return R.success(page);
    }

}


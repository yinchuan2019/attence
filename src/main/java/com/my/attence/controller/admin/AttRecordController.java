package com.my.attence.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.common.DataResult;
import com.my.attence.entity.AttRecord;
import com.my.attence.modal.Dto.AttRecordDto;
import com.my.attence.service.AttRecordService;
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
 * @since 2020-12-03
 */
@RestController
@RequestMapping("/attence")
public class AttRecordController {
    @Resource
    private AttRecordService attRecordService;
    @Resource
    private AttStudentService attStudentService;


    @PostMapping("/record")
    @ApiOperation(value = "新增接口")
    public DataResult add(@RequestBody @Valid AttRecordDto dto) {
        AttRecord entity = new AttRecord();
        BeanUtil.copyProperties(dto,entity);
        attRecordService.save(entity);
        return DataResult.success();
    }

    @DeleteMapping("/record")
    @ApiOperation(value = "删除接口")
    public DataResult delete(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            attRecordService.removeById(id);
        }
        return DataResult.success();
    }

    @PutMapping("/record")
    @ApiOperation(value = "更新信息接口")
    public DataResult update(@RequestBody AttRecordDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return DataResult.fail("id不能为空");
        }
        AttRecord entity = new AttRecord();
        BeanUtil.copyProperties(dto,entity);
        attRecordService.updateById(entity);
        return DataResult.success();
    }

    @GetMapping("/record")
    @ApiOperation(value = "查询详情接口")
    public DataResult detailInfo(@RequestBody Long id) {
        AttRecord entity = attRecordService.getById(id);
        return DataResult.success(entity);
    }

    @PostMapping("/records")
    @ApiOperation(value = "分页获取信息接口")
    public DataResult pageInfo(@RequestBody AttRecordDto dto) {
        Page p = new Page(dto.getPage(), dto.getLimit());
        LambdaQueryWrapper<AttRecord> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(dto.getAttNo())) {
            queryWrapper.like(AttRecord::getAttNo, dto.getAttNo());
        }
        if (!StringUtils.isEmpty(dto.getAttName())) {
            queryWrapper.like(AttRecord::getAttName, dto.getAttName());
        }
        if (!StringUtils.isEmpty(dto.getAttBeginDate())) {
            queryWrapper.gt(AttRecord::getAttBeginDate, dto.getAttBeginDate());
        }
        if (!StringUtils.isEmpty(dto.getAttEndDate())) {
            queryWrapper.lt(AttRecord::getAttEndDate, dto.getAttEndDate());
        }
        if (!StringUtils.isEmpty(dto.getAttType())) {
            queryWrapper.eq(AttRecord::getAttType, dto.getAttType());
        }
        queryWrapper.orderByDesc(AttRecord::getAttBeginDate);
        IPage<AttRecord> page = attRecordService.page(p, queryWrapper);
        return DataResult.success(page);
    }
}


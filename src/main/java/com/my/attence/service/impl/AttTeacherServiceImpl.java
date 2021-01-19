package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.AttTeacher;
import com.my.attence.mapper.AttTeacherMapper;
import com.my.attence.service.AttTeacherService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abel
 * @since 2020-11-30
 */
@Service
public class AttTeacherServiceImpl extends ServiceImpl<AttTeacherMapper, AttTeacher> implements AttTeacherService {
    @Override
    public AttTeacher findByLoginId(String loginId) {
        LambdaQueryWrapper<AttTeacher> eq = Wrappers.<AttTeacher>lambdaQuery().eq(AttTeacher::getLoginId, loginId);
        AttTeacher one = this.getOne(eq);
        return one;
    }
}

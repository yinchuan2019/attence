package com.my.attence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.AttStudent;
import com.my.attence.mapper.AttStudentMapper;
import com.my.attence.service.AttStudentService;
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
public class AttStudentServiceImpl extends ServiceImpl<AttStudentMapper, AttStudent> implements AttStudentService {

    @Override
    public AttStudent findByLoginId(String loginId) {
        LambdaQueryWrapper<AttStudent> eq = Wrappers.<AttStudent>lambdaQuery().eq(AttStudent::getLoginId, loginId);
        AttStudent one = this.getOne(eq);
        return one;
    }
}

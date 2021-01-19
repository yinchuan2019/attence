package com.my.attence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.AttTeacher;

;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abel
 * @since 2020-11-30
 */
public interface AttTeacherService extends IService<AttTeacher> {

    AttTeacher findByLoginId(String loginId);
}

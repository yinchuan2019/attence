package com.my.attence.mapper;

import com.my.attence.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author abel
 * @since 2020-11-24
 */
public interface StudentMapper extends BaseMapper<Student> {

    int countWithSql();

}

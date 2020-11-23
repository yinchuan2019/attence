package com.my.attence.service.impl;

import com.my.attence.entity.Student;
import com.my.attence.mapper.StudentMapper;
import com.my.attence.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abel
 * @since 2020-11-24
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}

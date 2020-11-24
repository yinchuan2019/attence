package com.my.attence.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.attence.entity.Student;
import com.my.attence.mapper.StudentMapper;
import com.my.attence.service.IStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abel
 * @since 2020-11-24
 */
@Controller
@RequestMapping("/attence")
public class StudentController {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private IStudentService iStudentService;


    @GetMapping(value = "/student")
    public String login() {

        Student student = studentMapper.selectById(1);
        int i = studentMapper.countWithSql();
        List<Student> list = iStudentService.list();

        IPage<Student> page = new Page<>(1, 2);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        IPage<Student> studentIPage = studentMapper.selectPage(page, queryWrapper);
        Student st = new Student();
        st.setAge(1);
        st.setStudentName("setStudentName");
        studentMapper.insert(st);
        LambdaUpdateWrapper<Student> updateWrapper = new UpdateWrapper<Student>().lambda();

        updateWrapper.eq(Student::getAge,99);
        studentMapper.update(st,updateWrapper);

        LambdaQueryWrapper<Student> deleteWrapper = new QueryWrapper<Student>().lambda();
        deleteWrapper.eq(Student::getAge,99);

        studentMapper.delete(deleteWrapper);

        return "admin/login";
    }


}


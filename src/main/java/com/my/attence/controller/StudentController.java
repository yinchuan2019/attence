package com.my.attence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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


        return "admin/login";
    }


}


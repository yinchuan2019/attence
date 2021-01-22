package com.my.attence.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Api(tags = "视图")
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/login")
    public String login(Model model) {
        /*Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index/home" ;
        }*/
        return "login" ;
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home" ;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        return "admin/admin_list" ;
    }

    @GetMapping("/admin/info")
    public String userDetail(Model model) {
        this.addAttribute(model);
        return "admin/admin_edit" ;
    }

    @GetMapping("/admin/password")
    public String updatePassword(Model model) {
        return "admin/update_password" ;
    }

    @GetMapping("/menus")
    public String menusList(Model model) {
        return "menus/menu_list" ;
    }

    @GetMapping("/roles")
    public String roleList(Model model) {
        return "roles/role_list" ;
    }


    @GetMapping("/student")
    public String studentList(Model model) {
        return "student/student_list" ;
    }

    @GetMapping("/teacher")
    public String teacherList(Model model) {
        return "teacher/teacher_list" ;
    }

    @GetMapping("/record")
    public String recordList(Model model) {
        return "record/record_list" ;
    }

    @GetMapping("/appointment")
    public String appointmentList(Model model) {
        return "appointment/appointment_list" ;
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403" ;
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404" ;
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500" ;
    }

    @GetMapping("/main")
    public String indexHome() {
        return "main" ;
    }

    @GetMapping("/about")
    public String about() {
        return "about" ;
    }

    @GetMapping("/sysDict")
    public String sysDict() {
        return "sysdict/list" ;
    }

    @GetMapping("/sysJob")
    public String sysJob() {
        return "sysjob/list";
    }

    @GetMapping("/sysFiles")
    public String sysFiles() {
        return "sysfiles/list";
    }


    private void addAttribute(Model model){
        model.addAttribute("add", "新增11111");
    }

}

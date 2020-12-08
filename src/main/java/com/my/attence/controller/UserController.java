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
@RequestMapping("/user")
public class UserController {

    /**
     * 前台
     */
    @GetMapping("/login")
    public String adminLogin() {
        return "user/login" ;
    }

    @GetMapping("/home")
    public String adminIndex() {
        return "user/home";
    }

}

package com.my.attence.controller;

import com.my.attence.constant.Constant;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by abel on 2020/11/26
 * TODO
 */
@Api(tags = "视图")
@Controller
@RequestMapping("/user")
public class Index2Controller {

    @GetMapping("/user_login")
    public String userLogin(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_login" ;
    }

    @GetMapping("/user_seat")
    public String userSeat(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_seat" ;
    }

    @GetMapping("/user_stu1")
    public String userStu1(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu1" ;
    }

    @GetMapping("/user_stu2")
    public String userStu2(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu2" ;
    }

    @GetMapping("/user_stu3")
    public String userStu3(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu3" ;
    }
    @GetMapping("/user_stu4")
    public String userStu4(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu4" ;
    }
    @GetMapping("/user_stu5")
    public String userStu5(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu5" ;
    }
    @GetMapping("/user_stu6")
    public String userStu6(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu6" ;
    }
    @GetMapping("/user_stu7")
    public String userStu7(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu7" ;
    }
    @GetMapping("/user_stu8")
    public String userStu8(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_stu8" ;
    }
    @GetMapping("/user_tea1")
    public String userTea1(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea1" ;
    }
    @GetMapping("/user_tea2")
    public String userTea2(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea2" ;
    }
    @GetMapping("/user_tea3")
    public String userTea3(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea3" ;
    }
    @GetMapping("/user_tea4")
    public String userTea4(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea4" ;
    }
    @GetMapping("/user_tea5")
    public String userTea5(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea5" ;
    }
    @GetMapping("/user_tea6")
    public String userTea6(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea6" ;
    }
    @GetMapping("/user_tea7")
    public String userTea7(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea7" ;
    }
    @GetMapping("/user_tea8")
    public String userTea8(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea8" ;
    }
    @GetMapping("/user_tea9")
    public String userTea9(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea9" ;
    }
    @GetMapping("/user_tea10")
    public String userTea10(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea10" ;
    }
    @GetMapping("/user_tea11")
    public String userTea11(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea11" ;
    }
    @GetMapping("/user_tea12")
    public String userTea12(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea12" ;
    }
    @GetMapping("/user_tea13")
    public String userTea13(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_tea13" ;
    }

    @GetMapping("/user_time")
    public String userTime(Model model, HttpServletRequest request) {
        addLanguage(request);
        return "user/user_time" ;
    }



    private void addLanguage(HttpServletRequest request){
        final String language = request.getParameter("language");
        if(StringUtils.isNotBlank(language)){
            Locale locale = new Locale("zh", "CN");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
        }
        Locale locale = new Locale("ja", "JP");
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
    }

}

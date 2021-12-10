package com.my.attence.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.common.R;
import com.my.attence.controller.admin.AttSalaryController;
import com.my.attence.entity.AttTeacher;
import com.my.attence.modal.request.AttRecordDto;
import com.my.attence.service.AttTeacherService;
import com.my.attence.service.MailService;
import com.my.attence.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by abel on 2021/2/14
 * TODO
 */

@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private AttTeacherService attTeacherService;
    @Autowired
    private AttSalaryController attSalaryController;

    @PostMapping
    public R index() {
        try {
            mailService.sendSimpleMail("abel09@qq.com", "这是一封普通的邮件", "这是一封普通的测试邮件");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new R(-1, "邮件发送失败!!");
        }
        return new R();
    }

    @PostMapping("/htmlEmail")
    public R htmlEmail() {
        try {
            mailService.sendHtmlMail("11111@qq.com", "这是一HTML的邮件", "<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n"
                    + "	<div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n"
                    + "		<h3>欢迎使用IJPay -By Javen</h3>\n"
                    + "		<span>https://github.com/Javen205/IJPay</span>"
                    + "		<div\n"
                    + "			style=\"text-align: center; padding: 10px\"><a style=\"text-decoration: none;\" href=\"https://github.com/Javen205/IJPay\" target=\"_bank\" ><strong>IJPay 让支付触手可及,欢迎Start支持项目发展:)</strong></a></div>\n"
                    + "		<div\n" + "			style=\"text-align: center; padding: 4px\">如果对你有帮助,请任意打赏</div>\n"
                    + "		<img width=\"180px\" height=\"180px\"\n"
                    + "			src=\"https://oscimg.oschina.net/oscnet/8e86fed2ee9571eb133096d5dc1b3cb2fc1.jpg\">\n"
                    + "	</div>\n" + "</body>");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new R(-1, "邮件发送失败!!");
        }
        return new R();
    }

    @PostMapping("/attachmentsMail")
    public R attachmentsMail() {
        try {
            String filePath = "C:\\Users\\Administrator\\Pictures\\Feedback\\1.jpg";
            mailService.sendAttachmentsMail("11111@qq.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new R(-1, "邮件发送失败!!");
        }
        return new R();
    }

    @PostMapping("/resourceMail")
    public R resourceMail() {
        try {
            String rscId = "IJPay";
            String content = "<html><body>这是有图片的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
            String imgPath = "/Users/Javen/Desktop/IJPay.png";
            mailService.sendResourceMail("11111@163.com@163.com", "这邮件中含有图片", content, imgPath, rscId);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new R(-1, "邮件发送失败!!");
        }
        return new R();
    }

    @PostMapping("/templateMail")
    public R templateMail() {
        try {
            Context context = new Context();
            context.setVariable("project", "IJPay");
            context.setVariable("author", "Javen");
            context.setVariable("url", "https://github.com/Javen205/IJPay");
            String emailContent = templateEngine.process("emailTemp", context);

            mailService.sendHtmlMail("11111@qq.com", "这是模板邮件", emailContent);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new R(-1, "邮件发送失败!!");
        }
        return new R();
    }

    //@Scheduled(cron="0/5 * * * * ?")
    @Scheduled(cron="0 0 0 26 * ? ")
    public void scheduledMail() {
        LambdaQueryWrapper<AttTeacher> eq = Wrappers.<AttTeacher>lambdaQuery()
                .eq(AttTeacher::getTeaStatus, 1);
        List<AttTeacher> list = attTeacherService.list();
        for (AttTeacher e : list) {
            AttRecordDto dto = new AttRecordDto();
            dto.setTeaNo(e.getLoginId());
            dto.setBeginDate(LocalDateTime.of(DateUtils.getTodayBegin(), LocalTime.parse("00:00:00")));
            List<AttRecordDto> attRecordDtos = attSalaryController.findsSalarys(dto);
            if(CollectionUtils.isEmpty(attRecordDtos)){
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<html>\n");
            sb.append("<body>\n");
            sb.append("<table border=\"1\">\n");
            sb.append("<tr>\n");
//            sb.append("  <th>签到类型</th>\n");
            sb.append("  <th>类型</th>\n");
            sb.append("  <th>薪资</th>\n");
            sb.append("</tr>\n");

            for (AttRecordDto attRecord :attRecordDtos) {
                sb.append("<tr>\n");
                String str = "<td>" + attRecord.getWorkType() + "</td>\n" + "<td>" + attRecord.getSalary() + "</td>\n";
                sb.append("str");
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            sb.append("</body>\n");
            sb.append("</html>");
            mailService.sendSimpleMail(e.getTeaEmail(), "工资",sb.toString());
        }
    }

    //@PostConstruct
    public void doTest(){
        scheduledMail();
    }
}

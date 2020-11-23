package com.my.attence.controller;


import com.my.attence.entity.CcKbsAnnex;
import com.my.attence.mapper.CcKbsAnnexMapper;
import com.my.attence.service.ICcKbsAnnexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author abel
 * @since 2020-11-23
 */
@Controller
@RequestMapping("/attence")
public class CcKbsAnnexController {
    @Resource
    private ICcKbsAnnexService iCcKbsAnnexService;
    @Resource
    private CcKbsAnnexMapper ccKbsAnnexMapper;

    @GetMapping(value = "/ccKbsAnnex")
    public String profile() {
        CcKbsAnnex ccKbsAnnex = ccKbsAnnexMapper.selectById(1);
        ccKbsAnnex.getId();
        return "admin/header";
    }
}


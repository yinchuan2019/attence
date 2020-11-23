package com.my.attence.controller.admin;

import com.my.attence.constant.WebConst;
import com.my.attence.controller.BaseController;
import com.my.attence.dto.Types;
import com.my.attence.exception.TipException;
import com.my.attence.modal.Bo.RestResponseBo;
import com.my.attence.modal.Vo.UserVo;
import com.my.attence.service.IDemoService;
import com.my.attence.service.ILogService;
import com.my.attence.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
  * Created by abel on 2020/11/20
  * TODO
  */
@Controller
@RequestMapping("demo")
public class DemoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    public static final String CLASSPATH = TaleUtils.getUplodFilePath();

    @Resource
    private IDemoService attachService;

    @Resource
    private IDemoService demoService;

    @Resource
    private ILogService logService;

    
    /**
     * Created by abel on 2020/11/20
     * TODO
     */
    @GetMapping(value = "")
    public String profile() {

        return "admin/profile";
    }

    /**
     * 上传文件接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        UserVo users = this.user(request);
        Integer uid = users.getUid();
        List<String> errorFiles = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                if (multipartFile.getSize() <= WebConst.MAX_FILE_SIZE) {
                    String fkey = TaleUtils.getFileKey(fname);
                    String ftype = TaleUtils.isImage(multipartFile.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType();
                    File file = new File(CLASSPATH+fkey);
                    try {
                        FileCopyUtils.copy(multipartFile.getInputStream(),new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    attachService.save(fname, fkey, ftype, uid);
                } else {
                    errorFiles.add(fname);
                }
            }
        } catch (Exception e) {
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok(errorFiles);
    }


}

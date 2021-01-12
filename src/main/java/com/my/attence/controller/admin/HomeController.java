package com.my.attence.controller.admin;

import com.my.attence.common.DataResult;
import com.my.attence.entity.SysAdmin;
import com.my.attence.service.HomeService;
import com.my.attence.utils.TaleUtils;
import com.my.attence.vo.resp.HomeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 首页
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "首页数据")
public class HomeController {
    @Resource
    private HomeService homeService;

    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult getHomeInfo(HttpServletRequest request) {
        SysAdmin login = TaleUtils.getLoginAdmin(request);
        DataResult result = DataResult.success();
        HomeVO homeInfo = homeService.getHomeInfo(login.getId());
        result.setData(homeInfo);
        return result;
    }
}

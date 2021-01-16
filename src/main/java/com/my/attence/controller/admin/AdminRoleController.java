package com.my.attence.controller.admin;

import com.my.attence.common.R;
import com.my.attence.service.AdminRoleService;
import com.my.attence.vo.req.AdminRoleOperationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户和角色关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织管理-用户和角色关联接口")
public class AdminRoleController {
    @Resource
    private AdminRoleService adminRoleService;

    @PostMapping("/user/role")
    @ApiOperation(value = "修改或者新增用户角色接口")
    public R operationUserRole(@RequestBody @Valid AdminRoleOperationReqVO vo) {
        adminRoleService.addUserRoleInfo(vo);
        return R.success();
    }
}

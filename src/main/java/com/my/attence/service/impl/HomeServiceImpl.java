package com.my.attence.service.impl;

import com.my.attence.entity.SysAdmin;
import com.my.attence.service.HomeService;
import com.my.attence.service.PermissionService;
import com.my.attence.service.AdminService;
import com.my.attence.vo.resp.HomeVO;
import com.my.attence.vo.resp.PermissionNode;
import com.my.attence.vo.resp.AdminInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Resource
    private AdminService adminService;
    @Resource
    private PermissionService permissionService;

    @Override
    public HomeVO getHomeInfo(Long userId) {

        SysAdmin sysAdmin = adminService.getById(userId);
        AdminInfoVO vo = new AdminInfoVO();

        if (sysAdmin != null) {
            BeanUtils.copyProperties(sysAdmin, vo);
        }

        List<PermissionNode> menus = permissionService.permissionTreeList(userId);

        HomeVO respVO = new HomeVO();
        respVO.setMenus(menus);
        respVO.setUserInfo(vo);

        return respVO;
    }
}

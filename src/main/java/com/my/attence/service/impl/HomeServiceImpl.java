package com.my.attence.service.impl;

import com.my.attence.entity.SysUser;
import com.my.attence.service.HomeService;
import com.my.attence.service.PermissionService;
import com.my.attence.service.UserService;
import com.my.attence.vo.resp.HomeVO;
import com.my.attence.vo.resp.PermissionNode;
import com.my.attence.vo.resp.UserInfoVO;
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
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @Override
    public HomeVO getHomeInfo(Long userId) {

        SysUser sysUser = userService.getById(userId);
        UserInfoVO vo = new UserInfoVO();

        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, vo);
        }

        List<PermissionNode> menus = permissionService.permissionTreeList(userId);

        HomeVO respVO = new HomeVO();
        respVO.setMenus(menus);
        respVO.setUserInfo(vo);

        return respVO;
    }
}

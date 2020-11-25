package com.my.attence.service.impl;

import com.my.attence.entity.SysUser;
import com.my.attence.service.HomeService;
import com.my.attence.service.PermissionService;
import com.my.attence.service.UserService;
import com.my.attence.vo.resp.HomeRespVO;
import com.my.attence.vo.resp.PermissionRespNode;
import com.my.attence.vo.resp.UserInfoRespVO;
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
    public HomeRespVO getHomeInfo(String userId) {

        SysUser sysUser = userService.getById(userId);
        UserInfoRespVO vo = new UserInfoRespVO();

        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, vo);
        }

        List<PermissionRespNode> menus = permissionService.permissionTreeList(userId);

        HomeRespVO respVO = new HomeRespVO();
        respVO.setMenus(menus);
        respVO.setUserInfo(vo);

        return respVO;
    }
}

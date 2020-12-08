package com.my.attence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.attence.entity.SysAdminRole;
import com.my.attence.vo.req.AdminRoleOperationReqVO;

import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface AdminRoleService extends IService<SysAdminRole> {

    /**
     * 根据userId获取绑定的角色id
     * @param userId userId
     * @return List
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 用户绑定角色
     * @param vo vo
     */
    void addUserRoleInfo(AdminRoleOperationReqVO vo);
}

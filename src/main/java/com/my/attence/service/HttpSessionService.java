package com.my.attence.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.my.attence.constant.Constant;
import com.my.attence.entity.SysRolePermission;
import com.my.attence.entity.SysAdminRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * session管理器
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class HttpSessionService {
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;


    /**
     * 根据token获取userid
     *
     * @param token token
     * @return userid
     */
    public static String getUserIdByToken(String token) {
        if (StringUtils.isBlank(token) || !token.contains("#")) {
            return "";
        } else {
            return token.substring(token.indexOf("#") + 1);
        }
    }

    /**
     * 获取参数中的token
     *
     * @return token
     */
    public String getTokenFromHeader() {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constant.ACCESS_TOKEN);
        }
        return token;
    }

    /**
     * 获取当前session信息
     *
     * @return session信息
     */
    public JSONObject getCurrentSession() {
        String token = getTokenFromHeader();
        if (null != token) {
            /*if (redisService.exists(userTokenPrefix + token)) {
                String sessionInfoStr = redisService.get(userTokenPrefix + token);
                return JSON.parseObject(sessionInfoStr);
            } else {
                return null;
            }*/
        } else {
            return null;
        }
        return null;
    }

    /**
     * 获取当前session信息 username
     *
     * @return username

    public String getCurrentUsername() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERNAME_KEY);
        } else {
            return null;
        }
    }*/

    /**
     * 获取当前session信息 UserId
     *
     * @return UserId

    public String getCurrentUserId() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERID_KEY);
        } else {
            return null;
        }
    }
     */


    /**
     * 使当前用户的token失效
     */
    public void abortUserByToken() {
        String token = getTokenFromHeader();
        //redisService.del(userTokenPrefix + token);
    }

    /**
     * 使所有用户的token失效
     */
    public void abortAllUserByToken() {
        String token = getTokenFromHeader();
        String userId = getUserIdByToken(token);
       // redisService.delKeys(userTokenPrefix + "*#" + userId);
    }

    /**
     * 使用户的token失效
     */
    public void abortUserById(String userId) {
        //redisService.delKeys(userTokenPrefix + "*#" + userId);
    }

    /**
     * 使多个用户的token失效
     */
    public void abortUserByUserIds(List<String> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (String id : userIds) {
                //redisService.delKeys(userTokenPrefix + "*#" + id);
            }

        }
    }

    /**
     * 根据用户id， 刷新redis用户权限
     *
     * @param userId userId

    public void refreshUerId(String userId) {
        Set<String> keys = redisService.keys("#" + userId);
        //如果修改了角色/权限， 那么刷新权限
        for (String key : keys) {
            JSONObject redisSession = JSON.parseObject(redisService.get(key));

            List<String> roleNames = getRolesByUserId(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                redisSession.put(Constant.ROLES_KEY, roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            redisSession.put(Constant.PERMISSIONS_KEY, permissions);

            Long redisTokenKeyExpire = redisService.getExpire(key);
            //刷新token绑定的角色权限
            redisService.setAndExpire(key, redisSession.toJSONString(), redisTokenKeyExpire);

        }
    }*/

    /**
     * 根据角色id， 刷新redis用户权限
     *
     * @param roleId roleId
     */
    public void refreshRolePermission(Long roleId) {
        List userIds = adminRoleService.listObjs(Wrappers.<SysAdminRole>lambdaQuery().select(SysAdminRole::getUserId).eq(SysAdminRole::getRoleId, roleId));;
        if (!userIds.isEmpty()) {
            for (Object userId : userIds) {
                //redisService.setAndExpire(redisPermissionRefreshKey + userId, String.valueOf(userId), redisPermissionRefreshExpire);
            }

        }
    }

    /**
     * 根据权限id， 刷新redis用户权限
     *
     * @param permissionId permissionId
     */
    public void refreshPermission(Long permissionId) {
        //根据权限id，获取所有角色id
        List<Object> roleIds = rolePermissionService.listObjs(Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getRoleId).eq(SysRolePermission::getPermissionId, permissionId));
        if (!roleIds.isEmpty()) {
            //根据角色id， 获取关联用户
            List<Object> userIds = adminRoleService.listObjs(Wrappers.<SysAdminRole>lambdaQuery().select(SysAdminRole::getUserId).in(SysAdminRole::getRoleId, roleIds));
            if (!userIds.isEmpty()) {
                //删除用户redis
                //userIds.parallelStream().forEach(userId ->
                        //redisService.setAndExpire(redisPermissionRefreshKey + userId, userId.toString(), redisPermissionRefreshExpire));
            }
        }
    }


    /**
     * 生成随机的token
     *
     * @return token
     */
    private String getRandomToken() {
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();

        // 根据length生成相应长度的随机字符串
        for (int i = 0; i < 32; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                randomStr.append((char) (random.nextInt(26) + temp));
            } else {
                randomStr.append(random.nextInt(10));
            }
        }

        return randomStr.toString();
    }


    private List<String> getRolesByUserId(Long userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(Long userId) {
        return permissionService.getPermissionsByUserId(userId);
    }

}

package com.my.attence.service;

import com.my.attence.vo.resp.HomeVO;

/**
 * 首页
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface HomeService {

    /**
     * 获取首页信息
     * @param userId userId
     * @return HomeRespVO
     */
    HomeVO getHomeInfo(Long userId);
}

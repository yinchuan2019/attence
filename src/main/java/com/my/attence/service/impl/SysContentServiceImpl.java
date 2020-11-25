package com.my.attence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.attence.entity.SysContentEntity;
import com.my.attence.mapper.SysContentMapper;
import com.my.attence.service.SysContentService;
import org.springframework.stereotype.Service;

/**
 * 内容 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysContentService")
public class SysContentServiceImpl extends ServiceImpl<SysContentMapper, SysContentEntity> implements SysContentService {


}
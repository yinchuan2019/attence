package com.my.attence.service.impl;

import com.my.attence.mapper.DemoMapper;
import com.my.attence.modal.Vo.AttachVo;
import com.my.attence.service.IDemoService;
import com.my.attence.utils.DateKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangq on 2017/3/20.
 */
@Service
public class DemoServiceImpl implements IDemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Resource
    private DemoMapper attachDao;

    @Override
    public AttachVo selectById(Integer id) {
        if(null != id){
            return attachDao.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public void save(String fname, String fkey, String ftype, Integer author) {
        AttachVo attach = new AttachVo();
        attach.setFname(fname);
        attach.setAuthorId(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateKit.getCurrentUnixTime());
        attachDao.insertSelective(attach);
    }

    @Override
    public void deleteById(Integer id) {
        if (null != id) {
            attachDao.deleteByPrimaryKey( id);
        }
    }
}

package com.my.attence.service;

import com.my.attence.modal.Vo.AttachVo;

/**
 * Created by wangq on 2017/3/20.
 */
public interface IDemoService {

    /**
     * 保存附件
     *
     * @param fname
     * @param fkey
     * @param ftype
     * @param author
     */
    void save(String fname, String fkey, String ftype, Integer author);

    /**
     * 根据附件id查询附件
     * @param id
     * @return
     */
    AttachVo selectById(Integer id);

    /**
     * 删除附件
     * @param id
     */
    void deleteById(Integer id);
}

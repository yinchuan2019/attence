package com.my.attence.service;

import com.my.attence.modal.Vo.CommentVo;
import com.my.attence.modal.Vo.CommentVoExample;
import com.my.attence.modal.Bo.CommentBo;

/**
 * Created by BlueT on 2017/3/16.
 */
public interface ICommentService {

    /**
     * 保存对象
     * @param commentVo
     */
    void insertComment(CommentVo commentVo);

    /**
     * 根据主键查询评论
     * @param coid
     * @return
     */
    CommentVo getCommentById(Integer coid);


    /**
     * 删除评论，暂时没用
     * @param coid
     * @param cid
     * @throws Exception
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     * @param comments
     */
    void update(CommentVo comments);

}

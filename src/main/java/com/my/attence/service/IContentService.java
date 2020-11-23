package com.my.attence.service;

import com.my.attence.modal.Vo.ContentVo;

/**
 * Created by Administrator on 2017/3/13 013.
 */
public interface IContentService {

//    /**
//     * 保存文章
//     * @param contentVo contentVo
//     */
//    void insertContent(ContentVo contentVo);

    /**
     * 发布文章
     * @param contents
     */
    void publish(ContentVo contents);

    /**
     * 根据id或slug获取文章
     *
     * @param id id
     * @return ContentVo
     */
    ContentVo getContents(String id);

    /**
     * 根据主键更新
     * @param contentVo contentVo
     */
    void updateContentByCid(ContentVo contentVo);


    /**
     * 根据文章id删除
     * @param cid
     */
    void deleteByCid(Integer cid);

    /**
     * 编辑文章
     * @param contents
     */
    void updateArticle(ContentVo contents);


    /**
     * 更新原有文章的category
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal,String newCatefory);
}

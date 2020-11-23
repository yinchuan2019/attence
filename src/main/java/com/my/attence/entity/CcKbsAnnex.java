package com.my.attence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author abel
 * @since 2020-11-23
 */
public class CcKbsAnnex extends Model<CcKbsAnnex> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 附件所属知识点id
     */
    private Long lId;

    /**
     * 所属知识点的版本号
     */
    private String versionBrief;

    /**
     * 附件文件名称
     */
    private String title;

    /**
     * 附件内容（存放的路径）
     */
    private String content;

    /**
     * 转换后的文件
     */
    private String convertres;

    /**
     * 创建者，上传者
     */
    private Long createBy;

    /**
     * 更新者id
     */
    private Long updateBy;

    /**
     * 状态（0表示被删除，1表示存在）
     */
    private String isStatus;


    public Long getId() {
        return id;
    }

    public CcKbsAnnex setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getlId() {
        return lId;
    }

    public CcKbsAnnex setlId(Long lId) {
        this.lId = lId;
        return this;
    }

    public String getVersionBrief() {
        return versionBrief;
    }

    public CcKbsAnnex setVersionBrief(String versionBrief) {
        this.versionBrief = versionBrief;
        return this;
    }



    public String getTitle() {
        return title;
    }

    public CcKbsAnnex setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CcKbsAnnex setContent(String content) {
        this.content = content;
        return this;
    }

    public String getConvertres() {
        return convertres;
    }

    public CcKbsAnnex setConvertres(String convertres) {
        this.convertres = convertres;
        return this;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public CcKbsAnnex setCreateBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }


    public Long getUpdateBy() {
        return updateBy;
    }

    public CcKbsAnnex setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }



    public String getIsStatus() {
        return isStatus;
    }

    public CcKbsAnnex setIsStatus(String isStatus) {
        this.isStatus = isStatus;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}

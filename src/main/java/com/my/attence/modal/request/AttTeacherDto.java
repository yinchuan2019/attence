package com.my.attence.modal.request;

import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author abel
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AttTeacherDto extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Integer id;



    /**
     * パスワード/密码
     */
    private String teaPwd;

    /**
     * 教师名（漢字）/老师姓名（汉字）
     */
    private String teaNmKanji;

    /**
     * 教师名（ローマ字）/老手姓名（罗马字）
     */
    private String teaNmRoma;

    /**
     * メール/Mail
     */
    private String teaEmail;

    /**
     * 国籍/国籍
     */
    private String teaKokuseki;

    /**
     * 性别/性别
     */
    private Integer teaSex;

    /**
     * 学歴/学历
     */
    private String teaEducation;

    /**
     * 指導領域/指导学科
     */
    private String teaMajor;

    /**
     * 授業時給/教课时薪
     */
    private String teaWage;

    /**
     * 面談·ミーティング·特別·事務給/其他时薪
     */
    private String teaOteherWage;

    /**
     * 交通費/通勤费
     */
    private String teaCommutingCost;

    /**
     * 銀行口座·支店名/银行信息
     */
    private String teaBank;

    private Integer teaStatus;


}

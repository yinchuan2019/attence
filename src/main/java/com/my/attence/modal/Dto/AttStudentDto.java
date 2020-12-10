package com.my.attence.modal.Dto;

import com.my.attence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 *
 * @author abel
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AttStudentDto extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 番号/学号
     */
    private Integer id;

    /**
     * 登録ID/登陆ID
     */
    private String stuId;

    /**
     * パスワード/密码
     */
    private String stuPwd;

    /**
     * 学生名（漢字）/学生姓名（汉字）
     */
    private String stuNmKanji;

    /**
     * 学生名（ローマ字）/学生姓名（罗马字）
     */
    private String stuNmRoma;

    /**
     * 国籍/国籍
     */
    private String stuKokuseki;

    /**
     * 性别/性别
     */
    private Integer stuSex;

    /**
     * 生年月日/生年月日
     */
    private LocalDate stuBirthday;

    /**
     * 残り授業時間/还剩课程时间
     */
    private Integer stuLeftTime;

    /**
     * コース/课程
     */
    private String stuCourse;

    /**
     * 専攻/专业
     */
    private String stuMajor;

    /**
     * 大学・院入学/进入大学时间
     */
    private String stuUniversity;

    /**
     * 進学決定/决定上学
     */
    private String stuDecision;

    /**
     * 契約書/契约书
     */
    private Integer stuContract;

    /**
     * 面談シート/面试表
     */
    private Integer stuInterview;

    /**
     * 学费/学费
     */
    private Integer stuGakuhi;

    /**
     * 契約日/契约开始日
     */
    private LocalDate stuKeiyakubi;

    /**
     * 授業開始/开始上课时间
     */
    private LocalDate stuStart;

    /**
     * 退学日/退学日
     */
    private LocalDate stuEnd;

    /**
     * 尚藝舎卒業/本机构毕业日期
     */
    private LocalDate stuShangEnd;

    /**
     * 日本語学校卒業/语言学校毕业日期
     */
    private LocalDate stuJpEnd;

    /**
     * 区分/区分
     */
    private Integer stuStatus;

    /**
     * 在籍日本語学校/正在上的日语学校
     */
    private String stuDoingJpSchool;

    /**
     * 母国出身校/母国学校
     */
    private String stuHomeSchool;

    /**
     * N2/N2是否合格
     */
    private Integer stuN2;

    /**
     * N1/N1是否合格
     */
    private Integer stuN1;

    /**
     * EJU/EJU是否合格
     */
    private Integer stuEju;

    /**
     * 来日時期/来日本的时间
     */
    private LocalDate stuWhenJp;

    /**
     * 電話番号/电话号
     */
    private String stuPhoneNum;

    /**
     * Wechat/微信号
     */
    private String stuWechat;

    /**
     * 経緯/背景
     */
    private String stuBackground;

    /**
     * 仲介会社名/中介公司名
     */
    private String stuBroker;

    /**
     * 受験校1/考试学校1
     */
    private String stuSchool1;

    /**
     * 日程/考试时间1
     */
    private String stuSchoolTime1;

    /**
     * 合否/是否合格1
     */
    private Integer stuPass1;

    /**
     * 受験校1/考试学校2
     */
    private String stuSchool2;

    /**
     * 日程/考试时间2
     */
    private String stuSchoolTime2;

    /**
     * 合否/是否合格2
     */
    private Integer stuPass2;

    /**
     * 受験校1/考试学校3
     */
    private String stuSchool3;

    /**
     * 日程/考试时间3
     */
    private String stuSchoolTime3;

    /**
     * 合否/是否合格3
     */
    private Integer stuPass3;

    /**
     * 受験校1/考试学校4
     */
    private String stuSchool4;

    /**
     * 日程/考试时间4
     */
    private String stuSchoolTime4;

    /**
     * 合否/是否合格4
     */
    private Integer stuPass4;

    /**
     * 受験校1/考试学校5
     */
    private String stuSchool5;

    /**
     * 日程/考试时间5
     */
    private String stuSchoolTime5;

    /**
     * 合否/是否合格5
     */
    private Integer stuPass5;




}

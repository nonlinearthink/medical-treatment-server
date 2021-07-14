package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.sql.Timestamp;

/**
 * 问诊记录表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConsultAsk {

    /**
     * 问诊id
     */
    @TableId
    private String consultId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 科室id
     */
    private String deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 医生职称代码
     */
    private Character doctorLevelCode;

    /**
     * 医生职称
     */
    private String doctorLevelName;

    /**
     * 操作用户id
     */
    private String createUserId;

    /**
     * 配药人姓名
     */
    private String personName;

    /**
     * 配药人证件类型，01居民身份证 02居民户口簿 03护照 04军官证 05驾驶证 06港澳居民来往内地通行证 07台湾居民来往内地通行证 11出生证明 12港澳居民身份证 13港澳居民居住证 99其他法定有效证件
     */
    private Character personCardType;

    /**
     * 配药人证件号码
     */
    private String personCardId;

    /**
     * 配药人性别，1男，2女
     */
    private Character personGenderCode;

    /**
     * 配药人性别，1男，2女
     */
    private String personGenderName;

    /**
     * 配药人出生日期
     */
    private Timestamp personBirthDate;

    /**
     * 配药人年龄
     */
    private Integer personAge;

    /**
     * 配药人手机号码
     */
    private String personPhoneNo;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 诊断小结
     */
    private String diagnosis;

    /**
     * 复诊提交药物id，用英文逗号分隔
     */
    private String drugIds;

    /**
     * 复诊配药提交药物名称，用英文逗号分隔
     */
    private String drugNames;

    /**
     * 问诊照片id，用英文逗号分隔
     */
    private String photoIds;

    /**
     * 复诊配药状态，1待接诊，2进行中，3已完成
     */
    private Integer consultStatus;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 接诊时间
     */
    private Timestamp acceptTime;

    /**
     * 完成时间
     */
    private Timestamp finishTime;


}
package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.sql.Timestamp;

/**
 * 问诊人表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BasePatient {

    /**
     * 问诊人id
     */
    @TableId(type = IdType.AUTO)
    private Integer patientId;

    /**
     * 问诊人姓名
     */
    private String patientName;

    /**
     * 问诊人证件类型，01居民身份证 02居民户口簿 03护照 04军官证 05驾驶证 06港澳居民来往内地通行证 07台湾居民来往内地通行证 11出生证明 12港澳居民身份证 13港澳居民居住证 99其他法定有效证件
     */
    private Character patientCardType;

    /**
     * 问诊人证件号码
     */
    private String patientCardId;

    /**
     * 问诊人性别，1男，2女
     */
    private Character patientGender;

    /**
     * 问诊人出生日期
     */
    private Timestamp patientBirthDate;

    /**
     * 问诊人年龄
     */
    private Integer patientBirthAge;

    /**
     * 问诊人手机号码
     */
    private String personPhoneNo;

    /**
     * 创建用户id
     */
    private Integer createUserId;

}
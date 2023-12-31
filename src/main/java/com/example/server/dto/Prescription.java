package com.example.server.dto;

import com.example.server.entity.BaseDiagnosis;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Prescription {

    /**
     * 处方id
     */
    private Integer prescriptionId;

    /**
     * 机构id
     */
    private Integer orgId;

    /**
     * 机构名称
     */
    private String orgName;

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
     * 问题描述
     */
    private String question;

    /**
     * 确认诊断列表
     */
    private List<BaseDiagnosis> diagnosisList;

    /**
     * 问诊人性别，1男，2女
     */
    private Character patientGender;

    /**
     * 问诊人年龄
     */
    private Integer patientBirthAge;

    /**
     * 问诊人手机号码
     */
    private String patientPhoneNo;

    /**
     * 开方医生id
     */
    private Integer doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 处方药品列表
     */
    private List<PrescriptionDrugDetailResponse> prescriptionDrugList;

    /**
     * 开方时间
     */
    private Timestamp createTime;

}

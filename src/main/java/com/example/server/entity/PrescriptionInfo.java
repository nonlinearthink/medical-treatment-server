package com.example.server.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 处方表
 *
 * @author nonlinearthink
 */
@Data
public class PrescriptionInfo {

    /**
     * 处方id
     */
    private String prescriptionId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 患者id
     */
    private String userId;

    /**
     * 问诊id
     */
    private String consultId;

    /**
     * 处方类型，1西药，2中成药，3中草药
     */
    private Character prescriptionType;

    /**
     * 开方医生id
     */
    private String doctorId;

    /**
     * 开方医生姓名
     */
    private Character doctorName;

    /**
     * 开方时间
     */
    private Timestamp createTime;

    /**
     * 处方提交状态，0未提交 ，1已提交
     */
    private Character prescriptionStatus;

}
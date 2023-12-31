package com.example.server.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConsultRecordUserResponse {

    /**
     * 问诊id
     */
    private Integer consultId;

    /**
     * 操作用户id
     */
    private Integer creatorId;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 问诊人id
     */
    private Integer patientId;

    /**
     * 问诊人姓名
     */
    private String patientName;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 医生头像链接
     */
    private String avatarUrl;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 问题描述
     */
    private String question;

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

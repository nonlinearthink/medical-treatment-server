package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(type = IdType.AUTO)
    private Integer consultId;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 操作用户id
     */
    private Integer createUserId;

    /**
     * 配药人id
     */
    private Integer patientId;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 确认诊断id，用英文逗号分隔
     */
    private String diagnosisIds;

    /**
     * 复诊提交药物id，用英文逗号分隔
     */
    private String drugIds;

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
package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.sql.Timestamp;

/**
 * 问诊记录-医生端
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConsultRecordDoctor {

    /**
     * 问诊id
     */
    private Integer consultId;

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
     * 问诊人性别，1男，2女
     */
    private Character patientGender;

    /**
     * 问诊人年龄
     */
    private Integer patientBirthAge;

    /**
     * 复诊提交药物id，用英文逗号分隔
     */
    private String drugIds;

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

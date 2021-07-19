package com.example.server.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.server.entity.BaseDiagnosis;
import com.example.server.entity.BaseDrug;
import com.example.server.entity.ConsultAsk;
import com.example.server.entity.Photo;
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
public class ConsultAskResponse {

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
    private Integer creatorId;

    /**
     * 配药人id
     */
    private Integer patientId;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 确认诊断列表
     */
    private List<BaseDiagnosis> diagnosisList;

    /**
     * 复诊提交药物列表
     */
    private List<BaseDrug> drugList;

    /**
     * 问诊照片列表
     */
    private List<Photo> photoList;

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

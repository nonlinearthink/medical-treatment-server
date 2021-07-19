package com.example.server.dto;

import com.example.server.entity.BaseDiagnosis;
import com.example.server.entity.BaseDrug;
import com.example.server.entity.Photo;
import lombok.*;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConsultAskDataRequest {

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 操作用户id
     */
    private Integer creatorId;

    /**
     * 问诊人id
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

}

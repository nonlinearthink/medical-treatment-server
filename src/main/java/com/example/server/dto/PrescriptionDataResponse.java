package com.example.server.dto;

import com.example.server.entity.PrescriptionDrug;
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
public class PrescriptionDataResponse {

    /**
     * 处方id
     */
    private Integer prescriptionId;

    /**
     * 机构id
     */
    private Integer orgId;

    /**
     * 问诊id
     */
    private Integer consultId;

    /**
     * 处方类型，1西药，2中成药，3中草药
     */
    private Character prescriptionType;

    /**
     * 开方医生id
     */
    private Integer doctorId;

    /**
     * 处方药品列表
     */
    private List<PrescriptionDrug> prescriptionDrugList;

    /**
     * 处方提交状态，0未提交 ，1已提交
     */
    private Character prescriptionStatus;

    /**
     * 开方时间
     */
    private Timestamp createTime;

}

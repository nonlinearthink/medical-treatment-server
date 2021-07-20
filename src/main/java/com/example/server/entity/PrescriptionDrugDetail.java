package com.example.server.entity;

import lombok.*;

import java.math.BigDecimal;

/**
 * 处方药详情表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PrescriptionDrugDetail {

    /**
     * 处方药品标识
     */
    private Integer prescriptionDrugId;

    /**
     * 药品id
     */
    private Integer drugId;

    /**
     * 药品通用名称
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String specification;

    /**
     * 包装单位
     */
    private Character packUnit;

    /**
     * 药品价格
     */
    private BigDecimal price;

    /**
     * 剂量
     */
    private BigDecimal dose;

    /**
     * 剂量单位
     */
    private String doseUnit;

    /**
     * 用药频次id
     */
    private Integer drugFrequencyId;

    /**
     * 药品用法id
     */
    private Integer drugUsageId;

    /**
     * 用药天数
     */
    private Integer takeDays;

    /**
     * 药品数量
     */
    private BigDecimal quantity;

    /**
     * 组号
     */
    private Integer groupNumber;

    /**
     * 顺序号
     */
    private Integer sortNumber;

    /**
     * 嘱托
     */
    private String remark;

}

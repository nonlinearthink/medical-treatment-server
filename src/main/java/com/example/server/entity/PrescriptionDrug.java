package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;

/**
 * 处方药品表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PrescriptionDrug {

    /**
     * 处方药品标识
     */
    @TableId
    private String prescriptionDrugId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 处方id
     */
    private String prescriptionId;

    /**
     * 药品id
     */
    private Integer drugId;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String specification;

    /**
     * 一次剂量
     */
    private BigDecimal dose;

    /**
     * 剂量单位
     */
    private String doseUnit;

    /**
     * 用药频次代码
     */
    private Integer frequencyCode;

    /**
     * 用药频次
     */
    private String frequencyName;

    /**
     * 药品用法代码
     */
    private Integer usageCode;

    /**
     * 药品用法
     */
    private String usageName;

    /**
     * 用药天数
     */
    private Integer takeDays;

    /**
     * 药品数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 包装单位
     */
    private String packUnit;

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
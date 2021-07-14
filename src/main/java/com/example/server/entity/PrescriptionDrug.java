package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(type = IdType.AUTO)
    private Integer prescriptionDrugId;

    /**
     * 处方id
     */
    private Integer prescriptionId;

    /**
     * 药品id
     */
    private Integer drugId;

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
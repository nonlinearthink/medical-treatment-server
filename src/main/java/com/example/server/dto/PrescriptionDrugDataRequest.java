package com.example.server.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PrescriptionDrugDataRequest {

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
     * 组号
     */
    private Integer groupNumber;

    /**
     * 顺序号
     */
    private Integer sortNumber;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 嘱托
     */
    private String remark;

}

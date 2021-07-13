package com.example.server.entity;

import java.math.BigDecimal;

/**
 * 基础药品表
 *
 * @author nonlinearthink
 */
public class BaseDrug {

    /**
     * 药品id
     */
    private Integer drugId;

    /**
     * 药品通用名称
     */
    private String drugName;

    /**
     * 商品名
     */
    private String tradeName;

    /**
     * 拼音码
     */
    private String pinyinCode;

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
     * 产地
     */
    private String factoryName;

    /**
     * 批准文号
     */
    private String approvalNumber;

}
package com.example.server.entity;

import lombok.Data;

/**
 * 药品频次表
 *
 * @author nonlinearthink
 */
@Data
public class BaseDicDrugFrequency {

    /**
     * 项目代码
     */
    private Integer itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目名称缩写
     */
    private String itemNameAbbr;

}

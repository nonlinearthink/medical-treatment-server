package com.example.server.entity;

import lombok.Data;

/**
 * 药品用法表
 *
 * @author nonlinearthink
 */
@Data
public class BaseDicDrugUsage {

    /**
     * 项目代码
     */
    private Integer itemCode;

    /**
     * 项目名称
     */
    private String itemName;

}
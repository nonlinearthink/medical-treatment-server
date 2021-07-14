package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 药品频次表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseDicDrugFrequency {

    /**
     * 项目代码
     */
    @TableId
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

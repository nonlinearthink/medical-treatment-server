package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 药品用法表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseDicDrugUsage {

    /**
     * 项目代码
     */
    @TableId
    private Integer itemCode;

    /**
     * 项目名称
     */
    private String itemName;

}
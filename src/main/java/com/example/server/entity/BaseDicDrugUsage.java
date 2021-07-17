package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
     * 药品用法id
     */
    @TableId(type = IdType.AUTO)
    private Integer drugUsageId;

    /**
     * 药品用法名称
     */
    private String drugUsageName;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
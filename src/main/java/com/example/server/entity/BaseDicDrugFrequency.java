package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
     * 药品频次id
     */
    @TableId(type = IdType.AUTO)
    private Integer drugFrequencyId;

    /**
     * 药品频次名称
     */
    private String drugFrequencyName;

    /**
     * 药品频次名称缩写
     */
    private String drugFrequencyNameAbbr;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
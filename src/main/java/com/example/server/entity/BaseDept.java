package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 科室表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseDept {

    /**
     * 科室id
     */
    @TableId(type = IdType.AUTO)
    private Integer deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 机构id
     */
    private Integer orgId;

    /**
     * 创建者ID
     */
    private String creatorId;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
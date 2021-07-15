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
public class BaseOrg {

    /**
     * 机构id
     */
    @TableId(type = IdType.AUTO)
    private Integer orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 创建者ID
     */
    private String creatorId;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
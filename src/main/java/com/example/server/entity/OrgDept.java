package com.example.server.entity;

import lombok.*;

/**
 * 机构-科室对照表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OrgDept {

    /**
     * 机构id
     */
    private Integer orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 科室名称
     */
    private String deptName;

}

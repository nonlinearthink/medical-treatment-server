package com.example.server.entity;

import lombok.*;

/**
 * 科室-医生对照表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeptDoctor {

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 医生头像链接
     */
    private String avatarUrl;

    /**
     * 医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士
     */
    private Character levelCode;

    /**
     * 医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士
     */
    private String levelName;

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

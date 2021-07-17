package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DoctorDataRequest {

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 医生头像链接
     */
    private String avatarUrl;

    /**
     * 医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士
     */
    private Character levelCode;

}

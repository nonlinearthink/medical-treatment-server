package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 医生表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseDoctor {

    /**
     * 医生id
     */
    @TableId(type = IdType.AUTO)
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
     * 手机号码
     */
    private String phoneNo;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
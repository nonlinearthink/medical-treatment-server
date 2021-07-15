package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.sql.Timestamp;

/**
 * 管理员表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseAdmin {

    /**
     * 管理员id
     */
    @TableId
    private String adminId;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 管理员类型，1超级管理员，2普通管理员
     */
    private Character adminType;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 软删除标记
     */
    private Boolean deleteMark;

}
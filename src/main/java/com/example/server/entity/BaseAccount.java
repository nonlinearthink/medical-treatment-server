package com.example.server.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户表
 *
 * @author nonlinearthink
 */
@Data
public class BaseAccount {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型，1居民，2医生
     */
    private Character userType;

    /**
     * 微信小程序openid
     */
    private String miniOpenId;

    /**
     * 手机号码
     */
    private String phoneNo;

    /**
     * 创建时间
     */
    private Timestamp createTime;

}

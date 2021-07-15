package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminLoginResponse {

    /**
     * 登录认证信息，需在其他隐私数据请求的时候携带
     */
    private String token;

    /**
     * 管理员类型，1超级管理员，2普通管理员
     */
    private Character adminType;

}

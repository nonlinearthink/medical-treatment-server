package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MiniProgramLoginResponse {

    /**
     * 登录认证信息，需在其他隐私数据请求的时候携带
     */
    private String token;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 是否成功
     */
    private String message;
}

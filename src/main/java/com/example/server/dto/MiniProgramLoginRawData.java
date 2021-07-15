package com.example.server.dto;


import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MiniProgramLoginRawData {

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 手机号码
     */
    private String phoneNo;

}

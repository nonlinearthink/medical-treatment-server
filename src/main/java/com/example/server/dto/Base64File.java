package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Base64File {

    /**
     * Base64编码数据
     */
    private String base64Data;

    /**
     * 文件类型，支持jpg、jpeg、gif、png、ico
     */
    private String fileType;

}

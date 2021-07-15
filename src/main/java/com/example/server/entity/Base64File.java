package com.example.server.entity;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Base64File {

    private String base64Data;

    private String fileType;

}

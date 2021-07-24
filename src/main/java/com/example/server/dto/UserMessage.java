package com.example.server.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMessage {

    private String doctor;

    private String content;

    private String status;

    private Timestamp time;

}

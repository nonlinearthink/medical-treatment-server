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
public class DoctorMessage {

    private String patient;

    private Timestamp time;

    private String dept;

    private String disease;

}

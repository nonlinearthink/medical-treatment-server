package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DiagnosisDataRequest {

    /**
     * 诊断类型，1西医，2中医
     */
    private Character diagnosisType;

    /**
     * 诊断类型为西医时为疾病代码，为中医时为中医疾病代码
     */
    private String diseasesCode;

    /**
     * 诊断类型为西医时为疾病名称，为中医时为中医疾病名称
     */
    private String diseasesName;

    /**
     * 拼音码
     */
    private String pinyinCode;

}

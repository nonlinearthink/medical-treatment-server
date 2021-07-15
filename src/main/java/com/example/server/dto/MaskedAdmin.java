package com.example.server.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.sql.Timestamp;


/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MaskedAdmin {

    /**
     * 管理员id
     */
    @TableId
    private String adminId;

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

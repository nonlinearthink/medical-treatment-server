package com.example.server.dto;

import lombok.*;

/**
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PageResponse<T> {

    /**
     * 分页数据
     */
    private T data;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 数量
     */
    private Long total;

}

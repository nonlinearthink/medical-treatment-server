package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 照片表
 *
 * @author nonlinearthink
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Photo {

    /**
     * 照片id
     */
    @TableId(type = IdType.AUTO)
    private Integer photoId;

    /**
     * 照片地址
     */
    private String photoUrl;


}
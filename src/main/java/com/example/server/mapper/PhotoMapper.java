package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.entity.Photo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author nonlinearthink
 */
@Mapper
public interface PhotoMapper extends BaseMapper<Photo> {
}

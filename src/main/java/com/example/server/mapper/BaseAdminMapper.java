package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseAdminMapper extends BaseMapper<BaseAdmin> {

    /**
     * 分页查找管理员列表
     *
     * @param page 分页
     * @return 管理员列表
     */
    @Select("select * from base_admin")
    List<BaseAdmin> selectByPage(Page<BaseAdmin> page);

}

package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    IPage<BaseAdmin> selectByPage(Page<BaseAdmin> page);

    /**
     * 分页按条件查找管理员列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 管理员列表
     */
    @Select("select * from base_admin ${ew.customSqlSegment}")
    IPage<BaseAdmin> selectByPageConditional(Page<BaseAdmin> page,
                                             @Param(Constants.WRAPPER) Wrapper<BaseAdmin> wrapper);

}

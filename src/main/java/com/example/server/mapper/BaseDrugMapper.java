package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseDrug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseDrugMapper extends BaseMapper<BaseDrug> {

    /**
     * 分页查找机构列表
     *
     * @param page 分页
     * @return 机构列表
     */
    @Select("select * from base_drug")
    IPage<BaseDrug> selectByPage(Page<BaseDrug> page);

    /**
     * 分页按条件查找机构列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 机构列表
     */
    @Select("select * from base_drug ${ew.customSqlSegment}")
    IPage<BaseDrug> selectByPageConditional(Page<BaseDrug> page, @Param(Constants.WRAPPER) Wrapper<BaseDrug> wrapper);

}

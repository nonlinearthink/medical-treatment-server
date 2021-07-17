package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseDeptMapper extends BaseMapper<BaseDept> {

    /**
     * 分页查找机构列表
     *
     * @param page 分页
     * @return 机构列表
     */
    @Select("select * from base_dept")
    List<BaseDept> selectByPage(Page<BaseDept> page);

    /**
     * 分页按条件查找机构列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 机构列表
     */
    @Select("select * from base_dept ${ew.customSqlSegment}")
    List<BaseDept> selectByPageConditional(Page<BaseDept> page, @Param(Constants.WRAPPER) Wrapper<BaseDept> wrapper);

}

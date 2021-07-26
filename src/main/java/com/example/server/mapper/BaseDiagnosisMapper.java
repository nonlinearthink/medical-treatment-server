package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseDiagnosis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseDiagnosisMapper extends BaseMapper<BaseDiagnosis> {

    /**
     * 分页查找诊断列表
     *
     * @param page 分页
     * @return 诊断列表
     */
    @Select("select * from base_diagnosis")
    IPage<BaseDiagnosis> selectByPage(Page<BaseDiagnosis> page);

    /**
     * 分页按条件查找诊断列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 诊断列表
     */
    @Select("select * from base_diagnosis ${ew.customSqlSegment}")
    IPage<BaseDiagnosis> selectByPageConditional(Page<BaseDiagnosis> page,
                                                 @Param(Constants.WRAPPER) Wrapper<BaseDiagnosis> wrapper);

}

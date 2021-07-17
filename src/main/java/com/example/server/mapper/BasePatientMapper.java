package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BasePatient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BasePatientMapper extends BaseMapper<BasePatient> {

    /**
     * 分页查找问诊人列表
     *
     * @param page 分页
     * @return 问诊人列表
     */
    @Select("select * from base_patient")
    List<BasePatient> selectByPage(Page<BasePatient> page);

    /**
     * 分页按条件查找问诊人列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 问诊人列表
     */
    @Select("select * from base_patient ${ew.customSqlSegment}")
    List<BasePatient> selectByPageConditional(Page<BasePatient> page,
                                              @Param(Constants.WRAPPER) Wrapper<BasePatient> wrapper);

}

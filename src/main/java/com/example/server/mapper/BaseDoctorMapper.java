package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseDoctor;
import com.example.server.entity.DeptDoctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseDoctorMapper extends BaseMapper<BaseDoctor> {

    /**
     * 分页查找医生列表
     *
     * @param page 分页
     * @return 医生列表
     */
    @Select("select * from base_doctor")
    List<BaseDoctor> selectByPage(Page<DeptDoctor> page);

    /**
     * 分页按条件查找医生列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 医生列表
     */
    @Select("select * from base_doctor ${ew.customSqlSegment}")
    List<BaseDoctor> selectByPageConditional(Page<BaseDoctor> page,
                                             @Param(Constants.WRAPPER) Wrapper<BaseDoctor> wrapper);

}

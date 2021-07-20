package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.ConsultRecordDoctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface ConsultRecordDoctorMapper extends BaseMapper<ConsultRecordDoctor> {

    /**
     * 分页查找问诊列表
     *
     * @param page 分页
     * @return 医生列表
     */
    @Select("select * from consult_record_doctor")
    List<ConsultRecordDoctor> selectByPage(Page<ConsultRecordDoctor> page);

    /**
     * 分页按条件查找问诊记录列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 机构列表
     */
    @Select("select * from consult_record_doctor ${ew.customSqlSegment}")
    List<ConsultRecordDoctor> selectByPageConditional(Page<ConsultRecordDoctor> page,
                                                      @Param(Constants.WRAPPER) Wrapper<ConsultRecordDoctor> wrapper);

}

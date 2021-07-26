package com.example.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseDept;
import com.example.server.entity.BaseOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author nonlinearthink
 */
@Mapper
public interface BaseOrgMapper extends BaseMapper<BaseOrg> {

    /**
     * 分页查找机构列表
     *
     * @param page 分页
     * @return 机构列表
     */
    @Select("select * from base_org")
    IPage<BaseOrg> selectByPage(Page<BaseOrg> page);

    /**
     * 分页按条件查找机构列表
     *
     * @param page    分页
     * @param wrapper 条件构造器
     * @return 机构列表
     */
    @Select("select * from base_org ${ew.customSqlSegment}")
    IPage<BaseOrg> selectByPageConditional(Page<BaseOrg> page, @Param(Constants.WRAPPER) Wrapper<BaseOrg> wrapper);

}

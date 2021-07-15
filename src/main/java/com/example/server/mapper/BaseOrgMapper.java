package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseOrg;
import org.apache.ibatis.annotations.Mapper;
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
    List<BaseOrg> selectByPage(Page<BaseOrg> page);

}

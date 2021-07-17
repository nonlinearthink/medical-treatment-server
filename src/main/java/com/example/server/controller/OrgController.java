package com.example.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.entity.BaseOrg;
import com.example.server.mapper.BaseOrgMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/org")
public class OrgController {

    private final BaseOrgMapper baseOrgMapper;

    @Autowired
    public OrgController(BaseOrgMapper baseOrgMapper) {
        this.baseOrgMapper = baseOrgMapper;
    }

    /**
     * 管理员创建机构
     *
     * @param creatorId 创建者ID，从token中获取，请携带token，需是管理员
     * @param orgName   机构名称
     * @return 机构数据
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BaseOrg> createOrg(@RequestAttribute(name = "admin_id") String creatorId,
                                             @RequestParam(value = "orgName") String orgName) {
        log.info("添加机构请求");
        QueryWrapper<BaseOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_name", orgName);
        BaseOrg org = baseOrgMapper.selectOne(queryWrapper);
        if (org == null) {
            org = BaseOrg.builder().orgName(orgName).creatorId(creatorId).build();
            baseOrgMapper.insert(org);
        } else if (org.getDeleteMark()) {
            org.setDeleteMark(false);
            org.setCreatorId(creatorId);
            baseOrgMapper.updateById(org);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(org);
    }

    /**
     * 管理员删除机构
     *
     * @param operatorId 操作者ID，从token中获取，请携带token，需是管理员
     * @param orgId      机构id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{orgId}")
    public ResponseEntity<String> deleteOrg(@RequestAttribute(name = "admin_id") String operatorId,
                                            @PathVariable(value = "orgId") Integer orgId) {
        log.info("删除机构请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseOrg org = baseOrgMapper.selectById(orgId);
        if (org == null || org.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("机构不存在");
        } else {
            org.setDeleteMark(true);
            baseOrgMapper.updateById(org);
        }
        return ResponseEntity.ok("删除机构成功");
    }

    /**
     * 管理员更新机构名称
     *
     * @param operatorId 操作者ID，从token中获取，请携带token，需是管理员
     * @param orgId      机构id
     * @param orgName    机构名称
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{orgId}")
    public ResponseEntity<String> updateOrg(@RequestAttribute(name = "admin_id") String operatorId,
                                            @PathVariable(value = "orgId") Integer orgId,
                                            @RequestParam(value = "orgName") String orgName) {
        log.info("更新机构名称请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseOrg org = baseOrgMapper.selectById(orgId);
        if (org == null || org.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("机构不存在");
        } else {
            org.setOrgName(orgName);
            baseOrgMapper.updateById(org);
        }
        return ResponseEntity.ok("更新机构成功");
    }

    /**
     * 查询所有机构（分页、需携带token）
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 机构列表数据
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<BaseOrg>> queryAllOrg(@RequestParam(value = "number") Integer number,
                                                     @RequestParam(value = "size") Integer size) {
        log.info("查询所有机构请求");
        QueryWrapper<BaseOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_mark", false);
        List<BaseOrg> orgList = baseOrgMapper.selectByPageConditional(new Page<>(number, size), queryWrapper);
        return ResponseEntity.ok(orgList);
    }

}

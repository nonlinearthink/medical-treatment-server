package com.example.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.PageResponse;
import com.example.server.entity.BaseDept;
import com.example.server.mapper.BaseDeptMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科室管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/dept")
public class DeptController {

    private final BaseDeptMapper baseDeptMapper;

    @Autowired
    public DeptController(BaseDeptMapper baseDeptMapper) {
        this.baseDeptMapper = baseDeptMapper;
    }

    /**
     * 管理员创建科室
     *
     * @param creatorId 创建者ID，从token中获取，请携带token，需是管理员
     * @param deptName  科室名称
     * @param orgId     机构id
     * @return 科室数据
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BaseDept> createDept(@RequestAttribute(name = "admin_id") String creatorId,
                                               @RequestParam(value = "deptName") String deptName,
                                               @RequestParam(value = "orgId") Integer orgId) {
        log.info("添加科室请求");
        QueryWrapper<BaseDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_name", deptName);
        queryWrapper.eq("org_id", orgId);
        BaseDept dept = baseDeptMapper.selectOne(queryWrapper);
        if (dept == null) {
            dept = BaseDept.builder().deptName(deptName).orgId(orgId).creatorId(creatorId).build();
            baseDeptMapper.insert(dept);
        } else if (dept.getDeleteMark()) {
            dept.setDeleteMark(false);
            dept.setCreatorId(creatorId);
            baseDeptMapper.updateById(dept);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(dept);
    }

    /**
     * 管理员删除科室
     *
     * @param operatorId 操作者ID，从token中获取，请携带token，需是管理员
     * @param deptId     科室id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{deptId}")
    public ResponseEntity<String> deleteDept(@RequestAttribute(name = "admin_id") String operatorId,
                                             @PathVariable(value = "deptId") Integer deptId) {
        log.info("删除科室请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDept dept = baseDeptMapper.selectById(deptId);
        if (dept == null || dept.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("机构不存在");
        } else {
            dept.setDeleteMark(true);
            baseDeptMapper.updateById(dept);
        }
        return ResponseEntity.ok("删除机构成功");
    }

    /**
     * 管理员更新科室信息
     *
     * @param deptId   科室id
     * @param deptName 科室名称
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{deptId}")
    public ResponseEntity<String> updateDept(@RequestAttribute(name = "admin_id") String operatorId,
                                             @PathVariable(value = "deptId") Integer deptId,
                                             @RequestParam(value = "deptName") String deptName) {
        log.info("更新科室名称请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDept dept = baseDeptMapper.selectById(deptId);
        if (dept == null || dept.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("科室不存在");
        } else {
            dept.setDeptName(deptName);
            baseDeptMapper.updateById(dept);
        }
        return ResponseEntity.ok("更新科室成功");
    }

    /**
     * 查询所有科室（分页、需携带token）
     *
     * @param orgId  机构id，不为空的时候请求这个机构下的所有科室，为空请求所有科室
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 科室列表数据
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<BaseDept>> queryAllDept(@RequestParam(value = "orgId", required = false) Integer orgId,
                                                       @RequestParam(value = "number") Integer number,
                                                       @RequestParam(value = "size") Integer size) {
        log.info("查询所有科室请求");
        QueryWrapper<BaseDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_mark", false);
        if (orgId != null) {
            queryWrapper.eq("org_id", orgId);
        }
        IPage<BaseDept> queryResult = baseDeptMapper.selectByPageConditional(new Page<>(number, size),
                queryWrapper);
        return ResponseEntity.ok(queryResult.getRecords());
    }

    /**
     * 查询所有科室v2（分页、需携带token）
     *
     * @param orgId  机构id，不为空的时候请求这个机构下的所有科室，为空请求所有科室
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 科室列表数据
     */
    @SneakyThrows
    @GetMapping("/v2")
    public ResponseEntity<PageResponse<List<BaseDept>>> queryAllDept2(@RequestParam(value = "orgId",
            required = false) Integer orgId,
                                                                      @RequestParam(value = "number") Integer number,
                                                                      @RequestParam(value = "size") Integer size) {
        log.info("查询所有科室请求v2");
        QueryWrapper<BaseDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_mark", false);
        if (orgId != null) {
            queryWrapper.eq("org_id", orgId);
        }
        IPage<BaseDept> queryResult = baseDeptMapper.selectByPageConditional(new Page<>(number, size), queryWrapper);
        return ResponseEntity.ok(
                PageResponse
                        .<List<BaseDept>>builder()
                        .data(queryResult.getRecords())
                        .success(true)
                        .total(queryResult.getTotal()).build()
        );
    }

}

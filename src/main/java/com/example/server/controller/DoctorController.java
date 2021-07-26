package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.DoctorDataRequest;
import com.example.server.dto.PageResponse;
import com.example.server.entity.BaseAccount;
import com.example.server.entity.BaseDoctor;
import com.example.server.entity.DeptDoctor;
import com.example.server.mapper.BaseAccountMapper;
import com.example.server.mapper.BaseDoctorMapper;
import com.example.server.mapper.DeptDoctorMapper;
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
 * 医生管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/doctor")
public class DoctorController {

    final Map<Character, String> levelMap = new HashMap<Character, String>(5) {{
        put('1', "主任医师");
        put('2', "副主任医师");
        put('3', "主治医师");
        put('4', "医师");
        put('5', "医士");
    }};

    private final BaseDoctorMapper baseDoctorMapper;
    private final DeptDoctorMapper deptDoctorMapper;
    private final BaseAccountMapper baseAccountMapper;

    @Autowired
    public DoctorController(BaseDoctorMapper baseDoctorMapper, DeptDoctorMapper deptDoctorMapper,
                            BaseAccountMapper baseAccountMapper) {
        this.baseDoctorMapper = baseDoctorMapper;
        this.deptDoctorMapper = deptDoctorMapper;
        this.baseAccountMapper = baseAccountMapper;
    }

    /**
     * 管理员创建医生
     *
     * @param creatorId  创建者ID，从token中获取，请携带token
     * @param doctorData 创建doctor需要的数据
     * @return 医生信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BaseDoctor> createDoctor(@RequestAttribute(name = "admin_id") String creatorId,
                                                   @RequestBody DoctorDataRequest doctorData) {
        log.info("添加医生请求");
        if (creatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        QueryWrapper<BaseDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_no", doctorData.getPhoneNo());
        BaseDoctor doctor = baseDoctorMapper.selectOne(queryWrapper);
        if (doctor == null) {
            doctor = BaseDoctor.builder()
                    .doctorName(doctorData.getDoctorName())
                    .avatarUrl(doctorData.getAvatarUrl())
                    .deptId(doctorData.getDeptId())
                    .levelCode(doctorData.getLevelCode())
                    .levelName(levelMap.get(doctorData.getLevelCode()))
                    .phoneNo(doctorData.getPhoneNo())
                    .build();
            baseDoctorMapper.insert(doctor);
        } else if (doctor.getDeleteMark()) {
            doctor.setDeleteMark(false);
            doctor.setDoctorName(doctorData.getDoctorName());
            doctor.setAvatarUrl(doctorData.getAvatarUrl());
            doctor.setLevelCode(doctorData.getLevelCode());
            doctor.setLevelName(doctorData.getDoctorName());
            doctor.setPhoneNo(doctorData.getPhoneNo());
            baseDoctorMapper.updateById(doctor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(doctor);
    }

    /**
     * 管理员删除医生
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param doctorId   医生id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@RequestAttribute(name = "admin_id") String operatorId,
                                               @PathVariable(value = "doctorId") Integer doctorId) {
        log.info("删除医生请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDoctor doctor = baseDoctorMapper.selectById(doctorId);
        if (doctor == null || doctor.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("医生不存在");
        } else {
            doctor.setDeleteMark(true);
            baseDoctorMapper.updateById(doctor);
        }
        return ResponseEntity.ok("删除医生成功");
    }

    /**
     * 管理员更新医生
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param doctorId   医生id
     * @param doctorData 更新doctor需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{doctorId}")
    public ResponseEntity<String> updateDoctor(@RequestAttribute(name = "admin_id") String operatorId,
                                               @PathVariable(value = "doctorId") Integer doctorId,
                                               @RequestBody DoctorDataRequest doctorData) {
        log.info("更新医生请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDoctor doctor = baseDoctorMapper.selectById(doctorId);
        if (doctor == null || doctor.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("医生不存在");
        } else {
            BeanUtil.copyProperties(doctorData, doctor, new CopyOptions().setIgnoreNullValue(true));
            baseDoctorMapper.updateById(doctor);
            return ResponseEntity.ok("更新医生成功");
        }
    }

    /**
     * 查询医生列表（分页、需携带token）
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 医生列表（带科室数据）
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<DeptDoctor>> queryAllDeptDoctor(@RequestParam(value = "number") Integer number,
                                                               @RequestParam(value = "size") Integer size) {
        log.info("查询所有医生请求");
        IPage<DeptDoctor> queryResult = deptDoctorMapper.selectByPage(new Page<>(number, size));
        return ResponseEntity.ok(queryResult.getRecords());
    }

    /**
     * 查询医生列表v2（分页、需携带token）
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 医生列表（带科室数据）
     */
    @SneakyThrows
    @GetMapping("/v2")
    public ResponseEntity<PageResponse<List<DeptDoctor>>> queryAllDeptDoctor2(@RequestParam(value = "number") Integer number,
                                                                              @RequestParam(value = "size") Integer size) {
        log.info("查询所有医生请求v2");
        IPage<DeptDoctor> queryResult = deptDoctorMapper.selectByPage(new Page<>(number, size));
        return ResponseEntity.ok(
                PageResponse
                        .<List<DeptDoctor>>builder()
                        .success(true)
                        .total(queryResult.getTotal())
                        .data(queryResult.getRecords())
                        .build());
    }

    /**
     * 根据科室id查询医生列表（分页、需携带token）
     *
     * @param deptId 科室id
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 医生列表（带科室数据）
     */
    @SneakyThrows
    @GetMapping("/dept")
    public ResponseEntity<List<DeptDoctor>> queryAllDeptDoctor(@RequestParam(value = "deptId") Integer deptId,
                                                               @RequestParam(value = "number") Integer number,
                                                               @RequestParam(value = "size") Integer size) {
        log.info("查询所有医生请求");
        IPage<DeptDoctor> queryResult = deptDoctorMapper.selectByPageConditional(new Page<>(number, size),
                new QueryWrapper<DeptDoctor>().eq("dept_id", deptId));
        return ResponseEntity.ok(queryResult.getRecords());
    }

    /**
     * 通过关键字搜索所有医生
     *
     * @param keyword 关键字
     * @return 诊断类型列表
     */
    @SneakyThrows
    @GetMapping("/search")
    public ResponseEntity<List<DeptDoctor>> searchAllDrug(@RequestParam(value = "keyword") String keyword) {
        log.info("查询所有医生请求");
        QueryWrapper<DeptDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("doctor_name", keyword);
        List<DeptDoctor> doctorList = deptDoctorMapper.selectList(queryWrapper);
        return ResponseEntity.ok(doctorList);
    }

    /**
     * 医生端查询绑定的医生
     *
     * @param operatorId 创建者ID，从token中获取，请携带token
     * @return 医生数据（带科室数据）
     */
    @SneakyThrows
    @GetMapping("/data")
    public ResponseEntity<BaseDoctor> queryBaseDoctorById(@RequestAttribute(name = "user_id") Integer operatorId) {
        log.info("根据doctorId查询DeptDoctor");
        BaseAccount account = baseAccountMapper.selectById(operatorId);
        QueryWrapper<BaseDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_no", account.getPhoneNo());
        BaseDoctor doctor = baseDoctorMapper.selectOne(queryWrapper);
        return ResponseEntity.ok(doctor);
    }

    /**
     * 根据ID查询医生数据（需携带token）
     *
     * @param doctorId 医生id
     * @return 医生数据（带科室数据）
     */
    @SneakyThrows
    @GetMapping("/{doctorId}")
    public ResponseEntity<DeptDoctor> queryDeptDoctorById(@PathVariable(value = "doctorId") Integer doctorId) {
        log.info("根据doctorId查询DeptDoctor");
        DeptDoctor doctor = deptDoctorMapper.selectOne(new QueryWrapper<DeptDoctor>().eq("doctor_id", doctorId));
        return ResponseEntity.ok(doctor);
    }

}

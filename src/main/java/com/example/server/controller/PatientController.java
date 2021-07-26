package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.PatientDataRequest;
import com.example.server.entity.BasePatient;
import com.example.server.mapper.BasePatientMapper;
import com.example.server.util.AgeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 问诊人管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/patient")
public class PatientController {

    private final BasePatientMapper basePatientMapper;

    @Autowired
    public PatientController(BasePatientMapper basePatientMapper) {
        this.basePatientMapper = basePatientMapper;
    }

    /**
     * 用户端创建问诊人（绑定）
     *
     * @param creatorId   创建者ID，从token中获取，请携带token
     * @param patientData 创建patient需要的数据
     * @return 问诊人信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BasePatient> createPatient(@RequestAttribute(name = "user_id") Integer creatorId,
                                                     @RequestBody PatientDataRequest patientData) {
        log.info("添加问诊人请求");
        QueryWrapper<BasePatient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_card_type", patientData.getPatientCardType());
        queryWrapper.eq("patient_card_id", patientData.getPatientCardType());
        queryWrapper.eq("creator_id", creatorId);
        BasePatient patient = basePatientMapper.selectOne(queryWrapper);
        if (patient == null || patient.getDeleteMark()) {
            patient = BasePatient.builder()
                    .patientName(patientData.getPatientName())
                    .patientCardType(patientData.getPatientCardType())
                    .patientCardId(patientData.getPatientCardId())
                    .patientGender(patientData.getPatientGender())
                    .patientBirthDate(patientData.getPatientBirthDate())
                    .patientBirthAge(AgeUtil.countAge(new Date(patientData.getPatientBirthDate().getTime())))
                    .patientPhoneNo(patientData.getPatientPhoneNo())
                    .creatorId(creatorId)
                    .build();
            basePatientMapper.insert(patient);
        } else {
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(patient);
    }

    /**
     * 用户端删除问诊人（解绑）
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param patientId  问诊人id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatient(@RequestAttribute(name = "user_id") Integer operatorId,
                                                @PathVariable(value = "patientId") Integer patientId) {
        log.info("删除问诊人请求");
        BasePatient patient = basePatientMapper.selectById(patientId);
        if (patient == null || patient.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("问诊人不存在");
        } else if (operatorId.equals(patient.getCreatorId())) {
            patient.setDeleteMark(true);
            basePatientMapper.updateById(patient);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        return ResponseEntity.ok("删除问诊人成功");
    }

    /**
     * 用户端更新问诊人信息
     *
     * @param operatorId  操作者ID，从token中获取，请携带token
     * @param patientId   问诊人id
     * @param patientData 更新patient需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{patientId}")
    public ResponseEntity<String> updatePatient(@RequestAttribute(name = "user_id") Integer operatorId,
                                                @PathVariable(value = "patientId") Integer patientId,
                                                @RequestBody PatientDataRequest patientData) {
        log.info("更新问诊人请求");
        BasePatient patient = basePatientMapper.selectById(patientId);
        if (patient == null || patient.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("问诊人不存在");
        } else if (operatorId.equals(patient.getCreatorId())) {
            BeanUtil.copyProperties(patientData, patient, new CopyOptions().setIgnoreNullValue(true));
            patient.setPatientBirthAge(AgeUtil.countAge(new Date(patient.getPatientBirthDate().getTime())));
            basePatientMapper.updateById(patient);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        return ResponseEntity.ok("更新问诊人成功");
    }

    /**
     * 根据问诊人id查询问诊人信息
     *
     * @param patientId 问诊人id
     * @return 问诊人信息
     */
    @SneakyThrows
    @GetMapping("/{patientId}")
    public ResponseEntity<BasePatient> queryPatientById(@PathVariable(value = "patientId") Integer patientId) {
        log.info("查询问诊人请求");
        BasePatient patient = basePatientMapper.selectById(patientId);
        return ResponseEntity.ok(patient);
    }

    /**
     * 用户端获取自己创建的问诊人列表
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param number     分页页号，从1开始
     * @param size       分页大小
     * @return 问诊人信息列表
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<BasePatient>> queryPatientByUser(@RequestAttribute(name = "user_id") Integer operatorId,
                                                                @RequestParam(value = "number") Integer number,
                                                                @RequestParam(value = "size") Integer size) {
        log.info("查询所有问诊人请求");
        QueryWrapper<BasePatient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator_id", operatorId);
        queryWrapper.eq("delete_mark",false);
        List<BasePatient> patientList = basePatientMapper.selectByPageConditional(new Page<>(number, size),
                queryWrapper);
        return ResponseEntity.ok(patientList);
    }

}

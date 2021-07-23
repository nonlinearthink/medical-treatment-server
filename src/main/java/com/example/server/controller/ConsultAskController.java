package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.*;
import com.example.server.entity.*;
import com.example.server.mapper.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 问诊记录管理和查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/consult-ask")
public class ConsultAskController {

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("doctor", '2');
    }};

    private final ConsultAskMapper consultAskMapper;
    private final BaseAccountMapper baseAccountMapper;
    private final ConsultRecordUserMapper consultRecordUserMapper;
    private final ConsultRecordDoctorMapper consultRecordDoctorMapper;
    private final DeptDoctorMapper deptDoctorMapper;
    private final BasePatientMapper basePatientMapper;
    private final BaseDiagnosisMapper baseDiagnosisMapper;
    private final BaseDrugMapper baseDrugMapper;
    private final PhotoMapper photoMapper;

    @Autowired
    public ConsultAskController(ConsultAskMapper consultAskMapper, BaseAccountMapper baseAccountMapper,
                                ConsultRecordUserMapper consultRecordUserMapper,
                                ConsultRecordDoctorMapper consultRecordDoctorMapper,
                                DeptDoctorMapper deptDoctorMapper, BasePatientMapper basePatientMapper,
                                BaseDiagnosisMapper baseDiagnosisMapper, BaseDrugMapper baseDrugMapper,
                                PhotoMapper photoMapper) {
        this.consultAskMapper = consultAskMapper;
        this.baseAccountMapper = baseAccountMapper;
        this.consultRecordUserMapper = consultRecordUserMapper;
        this.consultRecordDoctorMapper = consultRecordDoctorMapper;
        this.deptDoctorMapper = deptDoctorMapper;
        this.basePatientMapper = basePatientMapper;
        this.baseDiagnosisMapper = baseDiagnosisMapper;
        this.baseDrugMapper = baseDrugMapper;
        this.photoMapper = photoMapper;
    }

    /**
     * 用户创建问诊记录
     *
     * @param creatorId      创建者ID，从token中获取，请携带token
     * @param consultAskData 创建问诊记录需要的数据
     * @return 问诊记录信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<ConsultAskResponse> createConsultAsk(@RequestAttribute(name = "user_id") Integer creatorId,
                                                               @RequestBody ConsultAskDataRequest consultAskData) {
        log.info("创建问诊记录请求");
        BaseAccount account = baseAccountMapper.selectById(creatorId);
        if (!account.getUserType().equals(userTypeMap.get("user"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        ConsultAsk consultAsk = ConsultAsk.builder()
                .creatorId(creatorId)
                .doctorId(consultAskData.getDoctorId())
                .patientId(consultAskData.getPatientId())
                .diagnosisIds(
                        consultAskData
                                .getDiagnosisList()
                                .stream()
                                .map(item -> item.getDiagnosisId().toString())
                                .collect(Collectors.joining(","))
                )
                .drugIds(
                        consultAskData
                                .getDrugList()
                                .stream()
                                .map(item -> item.getDrugId().toString())
                                .collect(Collectors.joining(","))
                )
                .question(consultAskData.getQuestion())
                .photoIds(
                        consultAskData
                                .getPhotoList()
                                .stream()
                                .map(item -> item.getPhotoId().toString())
                                .collect(Collectors.joining(","))
                )
                .consultStatus(1)
                .createTime(new Timestamp(System.currentTimeMillis()))
                .build();
        consultAskMapper.insert(consultAsk);
        ConsultAskResponse response = ConsultAskResponse.builder().build();
        BeanUtil.copyProperties(consultAsk, response,
                new CopyOptions().setIgnoreProperties("diagnosisIds", "drugIds", "photoIds"));
        response.setDiagnosisList(consultAskData.getDiagnosisList());
        response.setDrugList(consultAskData.getDrugList());
        response.setPhotoList(consultAskData.getPhotoList());
        return ResponseEntity.ok(response);
    }

    /**
     * 用户删除问诊记录
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param consultId  问诊记录id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{consultId}")
    public ResponseEntity<String> deleteConsultAsk(@RequestAttribute(name = "user_id") Integer operatorId,
                                                   @PathVariable(name = "consultId") String consultId) {
        log.info("删除问诊记录请求");
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        if (!consultAsk.getCreatorId().equals(operatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        consultAskMapper.deleteById(consultId);
        return ResponseEntity.ok("删除问诊信息成功");
    }

    /**
     * 用户更新问诊记录
     *
     * @param operatorId     操作者ID，从token中获取，请携带token
     * @param consultId      问诊记录id
     * @param consultAskData 更新问诊记录需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{consultId}")
    public ResponseEntity<String> updateConsultAskByUser(@RequestAttribute(name = "user_id") Integer operatorId,
                                                         @PathVariable(name = "consultId") Integer consultId,
                                                         @RequestBody ConsultAskDataRequest consultAskData) {
        log.info("更新问诊记录请求(用户)");
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        if (!consultAsk.getCreatorId().equals(operatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BeanUtil.copyProperties(consultAskData, consultAsk,
                new CopyOptions().setIgnoreProperties("diagnosisIds", "drugIds", "photoIds").setIgnoreNullValue(true));
        if (consultAskData.getDiagnosisList() != null && !consultAskData.getDiagnosisList().isEmpty()) {
            consultAsk.setDiagnosisIds(
                    consultAskData
                            .getDiagnosisList()
                            .stream()
                            .map(item -> item.getDiagnosisId().toString())
                            .collect(Collectors.joining(","))
            );
        }
        if (consultAskData.getDrugList() != null && !consultAskData.getDrugList().isEmpty()) {
            consultAsk.setDrugIds(
                    consultAskData
                            .getDrugList()
                            .stream()
                            .map(item -> item.getDrugId().toString())
                            .collect(Collectors.joining(","))
            );
        }
        if (consultAskData.getPhotoList() != null && !consultAskData.getPhotoList().isEmpty()) {
            consultAsk.setPhotoIds(
                    consultAskData
                            .getPhotoList()
                            .stream()
                            .map(item -> item.getPhotoId().toString())
                            .collect(Collectors.joining(","))
            );

        }
        consultAskMapper.updateById(consultAsk);
        return ResponseEntity.ok("更新问诊记录成功");
    }

    /**
     * 医生更新问诊记录
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param consultId  问诊记录id
     * @param status     复诊配药状态，1待接诊，2进行中，3已完成
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{consultId}/consult-status")
    public ResponseEntity<String> updateConsultAskByDoctor(@RequestAttribute(name = "user_id") Integer operatorId,
                                                           @PathVariable(name = "consultId") String consultId,
                                                           @RequestParam(name = "status") Integer status) {
        log.info("更新问诊记录请求(医生)");
        BaseAccount account = baseAccountMapper.selectById(operatorId);
        DeptDoctor doctor = deptDoctorMapper.selectOne(
                new QueryWrapper<DeptDoctor>().eq("phone_no", account.getPhoneNo())
        );
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        if (!consultAsk.getDoctorId().equals(doctor.getDoctorId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        if (status == 2) {
            consultAsk.setConsultStatus(2);
            consultAsk.setAcceptTime(new Timestamp(System.currentTimeMillis()));
            consultAskMapper.updateById(consultAsk);
            return ResponseEntity.ok("成功");
        } else if (status == 3) {
            consultAsk.setConsultStatus(3);
            consultAsk.setFinishTime(new Timestamp(System.currentTimeMillis()));
            consultAskMapper.updateById(consultAsk);
            return ResponseEntity.ok("成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("参数不符合要求");
        }
    }

    /**
     * 用户查询自己的所有问诊记录数据（分页）
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param number     分页页号，从1开始
     * @param size       分页大小
     * @return 用户的所有问诊记录
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/record/user")
    public ResponseEntity<List<ConsultRecordUserResponse>> queryAllConsultRecordByUser(
            @RequestAttribute(name = "user_id") Integer operatorId,
            @RequestParam(value = "number") Integer number,
            @RequestParam(value = "size") Integer size) {
        log.info("查询所有问诊记录请求");
        List<ConsultRecordUserResponse> consultRecordList;
        QueryWrapper<ConsultRecordUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator_id", operatorId);
        consultRecordList =
                consultRecordUserMapper.selectByPageConditional(new Page<>(number, size), queryWrapper).stream().map(item -> {
                    ConsultRecordUserResponse response = new ConsultRecordUserResponse();
                    BeanUtil.copyProperties(item, response, CopyOptions.create().setIgnoreNullValue(true));
                    response.setPatientName(basePatientMapper.selectById(item.getPatientId()).getPatientName());
                    return response;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(consultRecordList);
    }

    /**
     * 医生查询所有问诊记录数据（分页）
     *
     * @param operatorId 操作者ID，从token中获取，请携带token
     * @param number     分页页号，从1开始
     * @param size       分页大小
     * @return 用户的所有问诊记录
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/record/doctor")
    public ResponseEntity<List<ConsultRecordDoctorResponse>> queryAllConsultRecordByDoctor(
            @RequestAttribute(name = "user_id") Integer operatorId,
            @RequestParam(value = "number") Integer number,
            @RequestParam(value = "size") Integer size) {
        log.info("查询所有问诊记录请求");
        BaseAccount account = baseAccountMapper.selectById(operatorId);
        DeptDoctor doctor = deptDoctorMapper.selectOne(
                new QueryWrapper<DeptDoctor>().eq("phone_no", account.getPhoneNo())
        );
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        List<ConsultRecordDoctor> consultRecordList;
        QueryWrapper<ConsultRecordDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", doctor.getDoctorId());
        consultRecordList = consultRecordDoctorMapper.selectByPageConditional(new Page<>(number, size), queryWrapper);
        return ResponseEntity.ok(consultRecordList.stream().map(item -> {
            ConsultRecordDoctorResponse response = new ConsultRecordDoctorResponse();
            BeanUtil.copyProperties(item, response, new CopyOptions().setIgnoreNullValue(true).setIgnoreProperties(
                    "drugIds"));
            response.setDrugList(Arrays.stream(item.getDrugIds().split(",")).map(baseDrugMapper::selectById).collect(Collectors.toList()));
            return response;
        }).collect(Collectors.toList()));
    }

    /**
     * 查询完整问诊信息
     *
     * @param consultId 问诊记录id
     * @return 完整问诊记录信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/{consultId}")
    public ResponseEntity<ConsultAskDataResponse> queryConsultAskData(@PathVariable String consultId) {
        log.info("查询完整问诊记录请求");
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        List<BaseDiagnosis> diagnosisList =
                Arrays.stream(consultAsk.getDiagnosisIds().split(",")).map(item -> baseDiagnosisMapper.selectById(Integer.valueOf(item))).collect(Collectors.toList());
        List<BaseDrug> drugList =
                Arrays.stream(consultAsk.getDrugIds().split(",")).map(item -> baseDrugMapper.selectById(Integer.valueOf(item))).collect(Collectors.toList());
        List<Photo> photoList =
                Arrays.stream(consultAsk.getPhotoIds().split(",")).map(item -> photoMapper.selectById(Integer.valueOf(item))).collect(Collectors.toList());
        ConsultAskDataResponse consultAskDataResponse = ConsultAskDataResponse.builder()
                .consultId(consultAsk.getConsultId())
                .doctor(deptDoctorMapper.selectById(consultAsk.getDoctorId()))
                .patient(basePatientMapper.selectById(consultAsk.getPatientId()))
                .question(consultAsk.getQuestion())
                .diagnosisList(diagnosisList)
                .drugList(drugList)
                .photoList(photoList)
                .consultStatus(consultAsk.getConsultStatus())
                .createTime(consultAsk.getCreateTime())
                .acceptTime(consultAsk.getAcceptTime())
                .finishTime(consultAsk.getFinishTime())
                .build();
        return ResponseEntity.ok(consultAskDataResponse);
    }

}

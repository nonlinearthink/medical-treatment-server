package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dto.Prescription;
import com.example.server.dto.PrescriptionDataRequest;
import com.example.server.dto.PrescriptionDataResponse;
import com.example.server.dto.PrescriptionDrugDetailResponse;
import com.example.server.entity.*;
import com.example.server.mapper.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
 * 处方管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/prescription")
public class PrescriptionController {

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("doctor", '2');
    }};

    private final PrescriptionInfoMapper prescriptionInfoMapper;
    private final BaseAccountMapper baseAccountMapper;
    private final DeptDoctorMapper deptDoctorMapper;
    private final BaseOrgMapper baseOrgMapper;
    private final BasePatientMapper basePatientMapper;
    private final ConsultAskMapper consultAskMapper;
    private final PrescriptionDrugDetailMapper prescriptionDrugDetailMapper;
    private final BaseDicDrugFrequencyMapper baseDicDrugFrequencyMapper;
    private final BaseDicDrugUsageMapper baseDicDrugUsageMapper;
    private final BaseDiagnosisMapper baseDiagnosisMapper;

    public PrescriptionController(PrescriptionInfoMapper prescriptionInfoMapper, BaseAccountMapper baseAccountMapper,
                                  DeptDoctorMapper deptDoctorMapper, BaseOrgMapper baseOrgMapper,
                                  BasePatientMapper basePatientMapper, ConsultAskMapper consultAskMapper,
                                  PrescriptionDrugDetailMapper prescriptionDrugDetailMapper,
                                  BaseDicDrugFrequencyMapper baseDicDrugFrequencyMapper,
                                  BaseDicDrugUsageMapper baseDicDrugUsageMapper,
                                  BaseDiagnosisMapper baseDiagnosisMapper) {
        this.prescriptionInfoMapper = prescriptionInfoMapper;
        this.baseAccountMapper = baseAccountMapper;
        this.deptDoctorMapper = deptDoctorMapper;
        this.baseOrgMapper = baseOrgMapper;
        this.basePatientMapper = basePatientMapper;
        this.consultAskMapper = consultAskMapper;
        this.prescriptionDrugDetailMapper = prescriptionDrugDetailMapper;
        this.baseDicDrugFrequencyMapper = baseDicDrugFrequencyMapper;
        this.baseDicDrugUsageMapper = baseDicDrugUsageMapper;
        this.baseDiagnosisMapper = baseDiagnosisMapper;
    }

    /**
     * 医生创建处方
     *
     * @param creatorId        创建者ID，从token中获取，请携带token
     * @param prescriptionData 创建处方需要的数据
     * @return 处方数据
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<PrescriptionInfo> createPrescriptionDrug(@RequestAttribute(name = "user_id") Integer creatorId,
                                                                   @RequestBody PrescriptionDataRequest prescriptionData) {
        log.info("创建处方请求");
        BaseAccount account = baseAccountMapper.selectById(creatorId);
        if (!account.getUserType().equals(userTypeMap.get("doctor"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        PrescriptionInfo prescriptionInfo = PrescriptionInfo.builder()
                .orgId(prescriptionData.getOrgId())
                .consultId(prescriptionData.getConsultId())
                .prescriptionType(prescriptionData.getPrescriptionType())
                .doctorId(prescriptionData.getDoctorId())
                .prescriptionDrugIds(
                        prescriptionData
                                .getPrescriptionDrugList()
                                .stream()
                                .map(item -> item.getPrescriptionDrugId().toString())
                                .collect(Collectors.joining(","))
                )
                .createTime(new Timestamp(System.currentTimeMillis()))
                .prescriptionStatus(prescriptionData.getPrescriptionStatus())
                .build();
        prescriptionInfoMapper.insert(prescriptionInfo);
        PrescriptionDataResponse response = PrescriptionDataResponse.builder().build();
        BeanUtil.copyProperties(prescriptionInfo, response,
                new CopyOptions().setIgnoreProperties("prescriptionDrugIds", "prescriptionDrugList").setIgnoreNullValue(true));
        response.setPrescriptionDrugList(prescriptionData.getPrescriptionDrugList());
        return ResponseEntity.ok(prescriptionInfo);
    }

    /**
     * 医生删除处方
     *
     * @param operatorId     操作者ID，从token中获取，请携带token
     * @param prescriptionId 处方id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<String> deletePrescriptionDrug(@RequestAttribute(name = "user_id") Integer operatorId,
                                                         @PathVariable String prescriptionId) {
        log.info("删除处方请求");
        PrescriptionInfo prescriptionInfo = prescriptionInfoMapper.selectById(prescriptionId);
        BaseAccount account = baseAccountMapper.selectById(operatorId);
        DeptDoctor doctor = deptDoctorMapper.selectOne(
                new QueryWrapper<DeptDoctor>().eq("phone_no", account.getPhoneNo())
        );
        if (doctor == null || prescriptionInfo == null || !doctor.getDoctorId().equals(prescriptionInfo.getDoctorId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        prescriptionInfoMapper.deleteById(prescriptionId);
        return ResponseEntity.ok("删除处方成功");
    }

    /**
     * 医生更新处方
     *
     * @param operatorId       操作者ID，从token中获取，请携带token
     * @param prescriptionId   处方id
     * @param prescriptionData 更新处方需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<String> updatePrescriptionDrug(@RequestAttribute(name = "user_id") Integer operatorId,
                                                         @PathVariable String prescriptionId,
                                                         @RequestBody PrescriptionDataRequest prescriptionData) {
        log.info("更新处方请求");
        PrescriptionInfo prescriptionInfo = prescriptionInfoMapper.selectById(prescriptionId);
        BaseAccount account = baseAccountMapper.selectById(operatorId);
        DeptDoctor doctor = deptDoctorMapper.selectOne(
                new QueryWrapper<DeptDoctor>().eq("phone_no", account.getPhoneNo())
        );
        if (doctor == null || prescriptionInfo == null || !doctor.getDoctorId().equals(prescriptionInfo.getDoctorId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BeanUtil.copyProperties(prescriptionData, prescriptionInfo,
                new CopyOptions().setIgnoreProperties("prescriptionDrugIds", "prescriptionDrugList").setIgnoreNullValue(false));
        prescriptionInfoMapper.updateById(prescriptionInfo);
        return ResponseEntity.ok("更新处方成功");
    }

    /**
     * 获取电子处方
     *
     * @param consultId 问诊id
     * @return 电子处方信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/{consultId}")
    public ResponseEntity<List<Prescription>> getPrescription(@PathVariable(name = "consultId") Integer consultId) {
        log.info("查询电子处方请求");
        List<PrescriptionInfo> prescriptionInfoList = prescriptionInfoMapper.selectList(
                new QueryWrapper<PrescriptionInfo>().eq("consult_id", consultId));
        List<Prescription> prescriptionList = prescriptionInfoList.stream().map(item -> {
            BaseOrg org = baseOrgMapper.selectById(item.getOrgId());
            ConsultAsk consultAsk = consultAskMapper.selectById(item.getConsultId());
            BasePatient patient = basePatientMapper.selectById(consultAsk.getPatientId());
            DeptDoctor doctor =
                    deptDoctorMapper.selectOne(new QueryWrapper<DeptDoctor>().eq("doctor_id", item.getDoctorId()));
            System.out.println("doctor");
            System.out.println(JSONObject.toJSONString(doctor));
            List<PrescriptionDrugDetailResponse> prescriptionDrugDetailResponseList =
                    Arrays.stream(item.getPrescriptionDrugIds().split(",")).map(item2 -> {
                        PrescriptionDrugDetail prescriptionDrugDetail =
                                prescriptionDrugDetailMapper.selectOne(new QueryWrapper<PrescriptionDrugDetail>().eq(
                                        "prescription_drug_id", Integer.valueOf(item2)));
                        return PrescriptionDrugDetailResponse.builder()
                                .prescriptionDrugId(prescriptionDrugDetail.getPrescriptionDrugId())
                                .drugId(prescriptionDrugDetail.getDrugId())
                                .drugName(prescriptionDrugDetail.getDrugName())
                                .specification(prescriptionDrugDetail.getSpecification())
                                .packUnit(prescriptionDrugDetail.getPackUnit())
                                .price(prescriptionDrugDetail.getPrice())
                                .dose(prescriptionDrugDetail.getDose())
                                .doseUnit(prescriptionDrugDetail.getDoseUnit())
                                .drugFrequency(baseDicDrugFrequencyMapper.selectById(prescriptionDrugDetail.getDrugFrequencyId()))
                                .drugUsage(baseDicDrugUsageMapper.selectById(prescriptionDrugDetail.getDrugUsageId()))
                                .takeDays(prescriptionDrugDetail.getTakeDays())
                                .quantity(prescriptionDrugDetail.getQuantity())
                                .groupNumber(prescriptionDrugDetail.getGroupNumber())
                                .sortNumber(prescriptionDrugDetail.getSortNumber())
                                .remark(prescriptionDrugDetail.getRemark())
                                .count(prescriptionDrugDetail.getCount())
                                .build();
                    }).collect(Collectors.toList());
            System.out.println(prescriptionDrugDetailResponseList);
            return Prescription.builder()
                    .prescriptionId(item.getPrescriptionId())
                    .orgId(org.getOrgId())
                    .question(consultAsk.getQuestion())
                    .diagnosisList(Arrays.stream(consultAsk.getDiagnosisIds().split(",")).map(baseDiagnosisMapper::selectById).collect(Collectors.toList()))
                    .orgName(org.getOrgName())
                    .patientName(patient.getPatientName())
                    .patientCardType(patient.getPatientCardType())
                    .patientCardId(patient.getPatientCardId())
                    .patientGender(patient.getPatientGender())
                    .patientBirthAge(patient.getPatientBirthAge())
                    .patientPhoneNo(patient.getPatientPhoneNo())
                    .doctorId(doctor.getDoctorId())
                    .doctorName(doctor.getDoctorName())
                    .prescriptionDrugList(prescriptionDrugDetailResponseList)
                    .createTime(item.getCreateTime())
                    .build();
        }).collect(Collectors.toList());
        return ResponseEntity.ok(prescriptionList);
    }

}

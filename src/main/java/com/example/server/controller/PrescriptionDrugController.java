package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dto.PrescriptionDrugDataRequest;
import com.example.server.dto.PrescriptionDrugDataResponse;
import com.example.server.entity.BaseAccount;
import com.example.server.entity.PrescriptionDrug;
import com.example.server.entity.PrescriptionDrugDetail;
import com.example.server.mapper.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 处方药品管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/prescription-drug")
public class PrescriptionDrugController {

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("doctor", '2');
    }};

    private final PrescriptionDrugMapper prescriptionDrugMapper;
    private final BaseAccountMapper baseAccountMapper;
    private final PrescriptionDrugDetailMapper prescriptionDrugDetailMapper;
    private final BaseDicDrugFrequencyMapper drugFrequencyMapper;
    private final BaseDicDrugUsageMapper drugUsageMapper;

    public PrescriptionDrugController(PrescriptionDrugMapper prescriptionDrugMapper,
                                      BaseAccountMapper baseAccountMapper,
                                      PrescriptionDrugDetailMapper prescriptionDrugDetailMapper,
                                      BaseDicDrugFrequencyMapper drugFrequencyMapper,
                                      BaseDicDrugUsageMapper drugUsageMapper) {
        this.prescriptionDrugMapper = prescriptionDrugMapper;
        this.baseAccountMapper = baseAccountMapper;
        this.prescriptionDrugDetailMapper = prescriptionDrugDetailMapper;
        this.drugFrequencyMapper = drugFrequencyMapper;
        this.drugUsageMapper = drugUsageMapper;
    }

    /**
     * 医生创建处方药品
     *
     * @param creatorId            创建者ID，从token中获取，请携带token
     * @param prescriptionDrugData 创建处方药品需要的数据
     * @return 处方药品信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<PrescriptionDrug> createPrescriptionDrug(@RequestAttribute(name = "user_id") Integer creatorId,
                                                                   @RequestBody PrescriptionDrugDataRequest prescriptionDrugData) {
        log.info("创建处方药品请求");
        BaseAccount account = baseAccountMapper.selectById(creatorId);
        if (!account.getUserType().equals(userTypeMap.get("doctor"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        PrescriptionDrug prescriptionDrug = PrescriptionDrug.builder()
                .drugId(prescriptionDrugData.getDrugId())
                .drugFrequencyId(prescriptionDrugData.getDrugFrequencyId())
                .drugUsageId(prescriptionDrugData.getDrugUsageId())
                .takeDays(prescriptionDrugData.getTakeDays())
                .quantity(prescriptionDrugData.getQuantity())
                .groupNumber(prescriptionDrugData.getGroupNumber())
                .sortNumber(prescriptionDrugData.getSortNumber())
                .remark(prescriptionDrugData.getRemark())
                .creatorId(creatorId)
                .count(prescriptionDrugData.getCount())
                .build();
        prescriptionDrugMapper.insert(prescriptionDrug);
        return ResponseEntity.ok(prescriptionDrug);
    }

    /**
     * 医生删除处方药品
     *
     * @param operatorId         操作者ID，从token中获取，请携带token
     * @param prescriptionDrugId 处方药品id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{prescriptionDrugId}")
    public ResponseEntity<String> deletePrescriptionDrug(@RequestAttribute(name = "user_id") Integer operatorId,
                                                         @PathVariable String prescriptionDrugId) {
        log.info("删除处方药品请求");
        PrescriptionDrug prescriptionDrug = prescriptionDrugMapper.selectById(prescriptionDrugId);
        if (!prescriptionDrug.getCreatorId().equals(operatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        prescriptionDrugMapper.deleteById(prescriptionDrugId);
        return ResponseEntity.ok("删除处方药品成功");
    }

    /**
     * 医生更新处方药品
     *
     * @param operatorId           操作者ID，从token中获取，请携带token
     * @param prescriptionDrugId   处方药品id
     * @param prescriptionDrugData 更新处方药品需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{prescriptionDrugId}")
    public ResponseEntity<String> updatePrescriptionDrug(@RequestAttribute(name = "user_id") Integer operatorId,
                                                         @PathVariable String prescriptionDrugId,
                                                         @RequestBody PrescriptionDrugDataRequest prescriptionDrugData) {
        log.info("更新处方药品请求");
        PrescriptionDrug prescriptionDrug = prescriptionDrugMapper.selectById(prescriptionDrugId);
        if (!prescriptionDrug.getCreatorId().equals(operatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BeanUtil.copyProperties(prescriptionDrugData, prescriptionDrug, new CopyOptions().setIgnoreNullValue(true));
        prescriptionDrugMapper.updateById(prescriptionDrug);
        prescriptionDrugMapper.deleteById(prescriptionDrugId);
        return ResponseEntity.ok("更新处方药品成功");
    }

    /**
     * 医生获取处方药品
     *
     * @param operatorId         操作者ID，从token中获取，请携带token
     * @param prescriptionDrugId 处方药品id
     * @return 处方药品信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/{prescriptionDrugId}")
    public ResponseEntity<PrescriptionDrugDataResponse> getPrescriptionDrugDetail(@RequestAttribute(name = "user_id") Integer operatorId,
                                                                                  @PathVariable String prescriptionDrugId) {
        log.info("查询处方药品请求");
        PrescriptionDrugDetail prescriptionDrugDetail =
                prescriptionDrugDetailMapper.selectOne(new QueryWrapper<PrescriptionDrugDetail>().eq(
                        "prescription_drug_id", prescriptionDrugId));
        PrescriptionDrugDataResponse response = new PrescriptionDrugDataResponse();
        BeanUtil.copyProperties(prescriptionDrugDetail, response,
                new CopyOptions().setIgnoreNullValue(true).setIgnoreProperties("drugFrequencyId", "drugUsageId"));
        response.setDrugFrequency(drugFrequencyMapper.selectById(prescriptionDrugDetail.getDrugFrequencyId()));
        response.setDrugUsage(drugUsageMapper.selectById(prescriptionDrugDetail.getDrugUsageId()));
        return ResponseEntity.ok(response);
    }

}

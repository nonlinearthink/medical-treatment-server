package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.server.dto.ConsultAskDataRequest;
import com.example.server.dto.ConsultAskResponse;
import com.example.server.entity.BaseAccount;
import com.example.server.entity.ConsultAsk;
import com.example.server.mapper.BaseAccountMapper;
import com.example.server.mapper.ConsultAskMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
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

    @Autowired
    public ConsultAskController(ConsultAskMapper consultAskMapper, BaseAccountMapper baseAccountMapper) {
        this.consultAskMapper = consultAskMapper;
        this.baseAccountMapper = baseAccountMapper;
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
                .consultId(creatorId)
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
     * @param creatorId 创建者ID，从token中获取，请携带token
     * @param consultId 问诊记录id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{consultId}")
    public ResponseEntity<String> deleteConsultAsk(@RequestAttribute(name = "user_id") Integer creatorId,
                                                   @PathVariable(name = "consultId") String consultId) {
        log.info("删除问诊记录请求");
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        if (!consultAsk.getCreatorId().equals(creatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        consultAskMapper.deleteById(consultId);
        return ResponseEntity.ok("删除问诊信息成功");
    }

    /**
     * 用户更新问诊记录
     *
     * @param creatorId      创建者ID，从token中获取，请携带token
     * @param consultId      问诊记录id
     * @param consultAskData 更新问诊记录需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{consultId}")
    public ResponseEntity<String> updateConsultAsk(@RequestAttribute(name = "user_id") Integer creatorId,
                                                   @PathVariable(name = "consultId") Integer consultId,
                                                   @RequestBody ConsultAskDataRequest consultAskData) {
        log.info("更新问诊记录请求");
        ConsultAsk consultAsk = consultAskMapper.selectById(consultId);
        if (!consultAsk.getCreatorId().equals(creatorId)) {
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

}

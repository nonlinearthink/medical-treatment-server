package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.DiagnosisDataRequest;
import com.example.server.entity.BaseDiagnosis;
import com.example.server.mapper.BaseDiagnosisMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诊断类型管理和查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/dept")
public class DiagnosisController {

    private final BaseDiagnosisMapper baseDiagnosisMapper;

    @Autowired
    public DiagnosisController(BaseDiagnosisMapper baseDiagnosisMapper) {
        this.baseDiagnosisMapper = baseDiagnosisMapper;
    }

    /**
     * 管理员创建诊断类型
     *
     * @param creatorId     创建者ID，从token中获取，请携带token
     * @param diagnosisData 创建诊断类型需要的数据
     * @return 诊断类型信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BaseDiagnosis> createDiagnosis(@RequestAttribute(name = "admin_id") Integer creatorId,
                                                         @RequestBody DiagnosisDataRequest diagnosisData) {
        log.info("添加诊断类型请求");
        if (creatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        QueryWrapper<BaseDiagnosis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diagnosis_type", diagnosisData.getDiagnosisType());
        queryWrapper.eq("diseases_code", diagnosisData.getDiseasesCode());
        BaseDiagnosis diagnosis = baseDiagnosisMapper.selectOne(queryWrapper);
        if (diagnosis == null || diagnosis.getDeleteMark()) {
            diagnosis = BaseDiagnosis.builder()
                    .diagnosisType(diagnosisData.getDiagnosisType())
                    .diseasesCode(diagnosisData.getDiseasesCode())
                    .diagnosisId(diagnosisData.getDiseasesId())
                    .diseasesName(diagnosisData.getDiseasesName())
                    .pinyinCode(diagnosisData.getPinyinCode())
                    .build();
            baseDiagnosisMapper.insert(diagnosis);
        } else {
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(diagnosis);
    }

    /**
     * 管理员删除诊断类型
     *
     * @param operatorId  操作者ID，从token中获取，请携带token，需是管理员
     * @param diagnosisId 诊断类型id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{diagnosisId}")
    public ResponseEntity<String> deleteDiagnosis(@RequestAttribute(name = "admin_id") Integer operatorId,
                                                  @PathVariable(value = "diagnosisId") Integer diagnosisId) {
        log.info("删除诊断类型请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDiagnosis diagnosis = baseDiagnosisMapper.selectById(diagnosisId);
        if (diagnosis == null || diagnosis.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("诊断类型不存在");
        } else {
            diagnosis.setDeleteMark(true);
            baseDiagnosisMapper.updateById(diagnosis);
        }
        return ResponseEntity.ok("删除诊断类型成功");
    }

    /**
     * 管理员更新诊断类型名称
     *
     * @param operatorId    操作者ID，从token中获取，请携带token，需是管理员
     * @param diagnosisId   诊断类型id
     * @param diagnosisData 更新诊断类型需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{diagnosisId}")
    public ResponseEntity<String> updateOrg(@RequestAttribute(name = "admin_id") String operatorId,
                                            @PathVariable(value = "diagnosisId") Integer diagnosisId,
                                            @RequestBody DiagnosisDataRequest diagnosisData) {
        log.info("更新诊断类型请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDiagnosis diagnosis = baseDiagnosisMapper.selectById(diagnosisId);
        if (diagnosis == null || diagnosis.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("诊断类型不存在");
        } else {
            BeanUtil.copyProperties(diagnosisData, diagnosis, new CopyOptions().setIgnoreNullValue(true));
            baseDiagnosisMapper.updateById(diagnosis);
        }
        return ResponseEntity.ok("更新诊断类型成功");
    }

    /**
     * 管理员查询诊断类型列表
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 诊断类型列表
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<BaseDiagnosis>> queryAllDiagnosis(@RequestParam(value = "number") Integer number,
                                                                 @RequestParam(value = "size") Integer size) {
        log.info("查询所有诊断类型请求");
        List<BaseDiagnosis> diagnosisList = baseDiagnosisMapper.selectByPage(new Page<>(number, size));
        return ResponseEntity.ok(diagnosisList);
    }

}

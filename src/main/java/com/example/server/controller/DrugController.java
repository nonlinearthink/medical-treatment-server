package com.example.server.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.DrugDataRequest;
import com.example.server.entity.BaseDrug;
import com.example.server.mapper.BaseDrugMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品管理和查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/drug")
public class DrugController {

    private final BaseDrugMapper baseDrugMapper;

    @Autowired
    public DrugController(BaseDrugMapper baseDrugMapper) {
        this.baseDrugMapper = baseDrugMapper;
    }

    /**
     * 管理员创建药品
     *
     * @param creatorId 创建者ID，从token中获取，请携带token
     * @param drugData  创建药品需要的数据
     * @return 诊断类型信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<BaseDrug> createDrug(@RequestAttribute(name = "admin_id") Integer creatorId,
                                               @RequestBody DrugDataRequest drugData) {
        log.info("添加药品请求");
        if (creatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        BaseDrug drug = BaseDrug.builder()
                .drugName(drugData.getDrugName())
                .tradeName(drugData.getTradeName())
                .pinyinCode(drugData.getPinyinCode())
                .specification(drugData.getSpecification())
                .packUnit(drugData.getPackUnit())
                .price(drugData.getPrice())
                .dose(drugData.getDose())
                .doseUnit(drugData.getDoseUnit())
                .factoryName(drugData.getFactoryName())
                .approvalNumber(drugData.getApprovalNumber())
                .build();
        baseDrugMapper.insert(drug);
        return ResponseEntity.ok(drug);
    }

    /**
     * 管理员删除药品
     *
     * @param operatorId 操作者ID，从token中获取，请携带token，需是管理员
     * @param drugId     药品id
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{drugId}")
    public ResponseEntity<String> deleteDrug(@RequestAttribute(name = "admin_id") Integer operatorId,
                                             @PathVariable String drugId) {
        log.info("删除药品请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDrug drug = baseDrugMapper.selectById(drugId);
        if (drug == null || drug.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("药品不存在");
        } else {
            drug.setDeleteMark(true);
            baseDrugMapper.updateById(drug);
            return ResponseEntity.ok("删除药品成功");
        }
    }

    /**
     * 管理员更新药品
     *
     * @param operatorId 操作者ID，从token中获取，请携带token，需是管理员
     * @param drugId     药品id
     * @param drugData   更新药品需要的数据
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{drugId}")
    public ResponseEntity<String> updateDrug(@RequestAttribute(name = "admin_id") Integer operatorId,
                                             @PathVariable String drugId, @RequestBody DrugDataRequest drugData) {
        log.info("更新药品请求");
        if (operatorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseDrug drug = baseDrugMapper.selectById(drugId);
        if (drug == null || drug.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("药品不存在");
        } else {
            BeanUtil.copyProperties(drugData, drug, new CopyOptions().setIgnoreNullValue(true));
            baseDrugMapper.updateById(drug);
            return ResponseEntity.ok("更新药品成功");
        }
    }

    /**
     * 查询所有药品列表
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 诊断类型列表
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<List<BaseDrug>> queryAllDrug(@RequestParam(value = "number") Integer number,
                                                       @RequestParam(value = "size") Integer size) {
        log.info("查询所有药品请求");
        List<BaseDrug> drugList = baseDrugMapper.selectByPage(new Page<>(number, size));
        return ResponseEntity.ok(drugList);
    }

    /**
     * 通过关键字搜索所有药品列表
     *
     * @param keyword 关键字
     * @return 诊断类型列表
     */
    @SneakyThrows
    @GetMapping("search")
    public ResponseEntity<List<BaseDrug>> searchAllDrug(@RequestParam(value = "keyword") String keyword) {
        log.info("查询所有药品请求");
        QueryWrapper<BaseDrug> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("drug_name", keyword)
                .or().like("pinyin_code", keyword)
                .or().like("factory_name", keyword);
        List<BaseDrug> drugList = baseDrugMapper.selectList(queryWrapper);
        return ResponseEntity.ok(drugList);
    }

}

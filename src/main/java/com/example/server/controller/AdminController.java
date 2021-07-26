package com.example.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.dto.MaskedAdmin;
import com.example.server.dto.PageResponse;
import com.example.server.entity.BaseAdmin;
import com.example.server.entity.BaseDept;
import com.example.server.mapper.BaseAdminMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 管理员管理与查询业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    private final BaseAdminMapper baseAdminMapper;

    public AdminController(BaseAdminMapper baseAdminMapper) {
        this.baseAdminMapper = baseAdminMapper;
    }

    /**
     * 超级管理员添加管理员
     *
     * @param creatorId 创建者ID，从token中获取，请携带token，需是超级管理员
     * @param adminId   新管理员ID
     * @param password  新管理员密码
     * @param adminType 管理员类型
     * @return 创建完的管理员
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseEntity<MaskedAdmin> createAdmin(@RequestAttribute(name = "admin_id") String creatorId,
                                                   @RequestParam(value = "adminId") String adminId,
                                                   @RequestParam(value = "password") String password,
                                                   @RequestParam(value = "adminType") Character adminType) {
        log.info("添加管理员请求");
        BaseAdmin creator = baseAdminMapper.selectById(creatorId);
        if (!creator.getAdminType().equals('1')) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        BaseAdmin admin = baseAdminMapper.selectById(adminId);
        if (admin == null) {
            admin = BaseAdmin.builder()
                    .adminId(adminId)
                    .password(DigestUtils.md5DigestAsHex(password.getBytes()))
                    .adminType(adminType)
                    .createTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            baseAdminMapper.insert(admin);
        } else if (admin.getDeleteMark()) {
            admin.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            admin.setAdminType(adminType);
            admin.setCreateTime(new Timestamp(System.currentTimeMillis()));
            admin.setDeleteMark(false);
            baseAdminMapper.updateById(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(
                MaskedAdmin.builder()
                        .adminId(admin.getAdminId())
                        .createTime(admin.getCreateTime())
                        .adminType(admin.getAdminType())
                        .build()
        );
    }

    /**
     * 超级管理员删除管理员
     *
     * @param operatorId 操作员ID，从token中获取，请携带token，需是超级管理员
     * @param adminId    需要删除的管理员ID
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@RequestAttribute(name = "admin_id") String operatorId,
                                              @PathVariable(value = "adminId") String adminId) {
        log.info("删除管理员请求");
        BaseAdmin operator = baseAdminMapper.selectById(operatorId);
        if (!operator.getAdminType().equals('1')) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseAdmin admin = baseAdminMapper.selectById(adminId);
        if (admin == null) {
            return ResponseEntity.ok("管理员不存在");
        } else {
            admin.setDeleteMark(true);
            baseAdminMapper.updateById(admin);
        }
        return ResponseEntity.ok("删除成功");
    }

    /**
     * 超级管理员更新管理员权限
     *
     * @param operatorId 操作员ID，从token中获取，请携带token，需是超级管理员
     * @param adminId    需要更新权限的管理员ID
     * @param adminType  权限，1超级管理员，2普通管理员
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{adminId}")
    public ResponseEntity<String> updateType(@RequestAttribute(name = "admin_id") String operatorId,
                                             @PathVariable(value = "adminId") String adminId,
                                             @RequestParam(value = "adminType") Character adminType) {
        log.info("更新管理员权限请求");
        BaseAdmin operator = baseAdminMapper.selectById(operatorId);
        if (!operator.getAdminType().equals('1')) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseAdmin admin = baseAdminMapper.selectById(adminId);
        if (admin == null || admin.getDeleteMark()) {
            return ResponseEntity.ok("管理员不存在");
        } else {
            admin.setAdminType(adminType);
            baseAdminMapper.updateById(admin);
        }
        return ResponseEntity.ok("更新权限成功");
    }

    /**
     * 超级管理员重置管理员密码
     *
     * @param operatorId 操作员ID，从token中获取，请携带token，需是超级管理员
     * @param adminId    需要更新权限的管理员ID
     * @param password   新密码
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{adminId}/password")
    public ResponseEntity<String> resetPassword(@RequestAttribute(name = "admin_id") String operatorId,
                                                @PathVariable(value = "adminId") String adminId,
                                                @RequestParam(value = "password") String password) {
        log.info("重置管理员密码请求");
        BaseAdmin operator = baseAdminMapper.selectById(operatorId);
        if (!operator.getAdminType().equals('1')) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("权限不足");
        }
        BaseAdmin admin = baseAdminMapper.selectById(adminId);
        if (admin == null || admin.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("管理员不存在");
        } else {
            admin.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            baseAdminMapper.updateById(admin);
            authRedisTemplate.opsForValue().set("admin@" + adminId, "", 1, TimeUnit.SECONDS);
            return ResponseEntity.ok("重置密码成功");
        }
    }

    /**
     * 管理员自己更新密码
     *
     * @param adminId     当前登录的管理员ID，从token中获取，请携带token
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestAttribute(name = "admin_id") String adminId,
                                                 @RequestParam(value = "oldPassword") String oldPassword,
                                                 @RequestParam(value = "newPassword") String newPassword) {
        log.info("更新管理员密码请求");
        BaseAdmin admin = baseAdminMapper.selectById(adminId);
        if (admin == null || admin.getDeleteMark()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("管理员不存在");
        } else if (admin.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            admin.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            baseAdminMapper.updateById(admin);
            authRedisTemplate.opsForValue().set("admin@" + adminId, "", 1, TimeUnit.SECONDS);
            return ResponseEntity.ok("更新密码成功");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("旧密码错误");
        }
    }

    /**
     * 管理员查询所有管理员（分页）
     *
     * @param number 分页页号，从1开始
     * @param size   分页大小
     * @return 脱敏的管理员列表数据
     */
    @SneakyThrows
    @GetMapping("")
    public ResponseEntity<PageResponse<List<MaskedAdmin>>> queryAllAdmin(@RequestParam(value = "number") Integer number,
                                                                         @RequestParam(value = "size") Integer size) {
        log.info("查询所有管理员请求");
        IPage<BaseAdmin> queryResult = baseAdminMapper.selectByPageConditional(new Page<>(number, size),
                new QueryWrapper<BaseAdmin>().eq("delete_mark", false));
        List<MaskedAdmin> maskedAdminList = queryResult.getRecords().stream().map(
                item -> MaskedAdmin.builder()
                        .adminId(item.getAdminId())
                        .adminType(item.getAdminType())
                        .createTime(item.getCreateTime())
                        .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(
                PageResponse
                        .<List<MaskedAdmin>>builder()
                        .data(maskedAdminList)
                        .total(queryResult.getTotal())
                        .success(true)
                        .build());
    }

}

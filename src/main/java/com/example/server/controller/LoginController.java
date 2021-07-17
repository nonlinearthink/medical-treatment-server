package com.example.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dto.AdminLoginResponse;
import com.example.server.dto.MiniProgramLoginRawData;
import com.example.server.dto.MiniProgramLoginResponse;
import com.example.server.entity.BaseAccount;
import com.example.server.entity.BaseAdmin;
import com.example.server.mapper.BaseAccountMapper;
import com.example.server.mapper.BaseAdminMapper;
import com.example.server.util.JwtUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/login")
public class LoginController {

    final String MINI_LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session";

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("doctor", '2');
    }};

    @Value("${mini-program.user.appid}")
    private String APPID_USER;

    @Value("${mini-program.user.secret}")
    private String SECRET_USER;

    @Value("${mini-program.doctor.appid}")
    private String APPID_DOCTOR;

    @Value("${mini-program.doctor.secret}")
    private String SECRET_DOCTOR;

    private final BaseAccountMapper baseAccountMapper;
    private final BaseAdminMapper baseAdminMapper;
    private final RestTemplate restTemplate;
    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    @Autowired
    public LoginController(BaseAccountMapper baseAccountMapper, BaseAdminMapper baseAdminMapper,
                           RestTemplate restTemplate) {
        this.baseAccountMapper = baseAccountMapper;
        this.baseAdminMapper = baseAdminMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * 微信小程序登录
     *
     * @param code     wx.login()获取的code
     * @param rawData  用户信息
     * @param userType 用户类型，可选值为user和doctor
     * @return token证书
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/miniprogram/{userType}")
    public ResponseEntity<MiniProgramLoginResponse> miniProgramLogin(@RequestParam(value = "code") String code,
                                                                     @RequestBody MiniProgramLoginRawData rawData,
                                                                     @PathVariable String userType) {
        // 动态绑定appid和secret
        log.info("微信小程序登录请求");
        String appid, secret;
        if ("user".equals(userType)) {
            appid = APPID_USER;
            secret = SECRET_USER;
        } else if ("doctor".equals(userType)) {
            appid = APPID_DOCTOR;
            secret = SECRET_DOCTOR;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        // 请求openid和sessionKey
        String uri = UriComponentsBuilder
                .fromUriString(MINI_LOGIN_API)
                .queryParam("appid", appid)
                .queryParam("secret", secret)
                .queryParam("js_code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JSONObject postResult = JSONObject.parseObject(
                restTemplate.postForObject(uri, new HttpEntity<>(headers), String.class));
        String openid = postResult.getString("openid");
        String sessionKey = postResult.getString("session_key");
        log.info("openid: " + openid + "    session_key: " + sessionKey);
        // 判断用户是否已经存在
        QueryWrapper<BaseAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mini_open_id", openid).eq("user_type", userTypeMap.get(userType));
        BaseAccount baseAccount = baseAccountMapper.selectOne(queryWrapper);
        System.out.println(baseAccount);
        if (baseAccount == null) {
            baseAccount = BaseAccount.builder()
                    .nickName(rawData.getNickName())
                    .avatarUrl(rawData.getAvatarUrl())
                    .userType(userTypeMap.get(userType))
                    .miniOpenId(openid)
                    .phoneNo(rawData.getPhoneNo())
                    .createTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            baseAccountMapper.insert(baseAccount);
            log.info("新用户注册成功: " + baseAccount.getUserId());
        }
        // 生成token
        Map<String, String> claims = new HashMap<>(2);
        claims.put("session_key", sessionKey);
        claims.put("user_id", baseAccount.getUserId().toString());
        String token = JwtUtil.createToken(claims);
        log.info("生成token: " + token);
        // 返回结果
        authRedisTemplate.opsForValue().set("wechat@" + baseAccount.getUserId().toString(), token);
        return ResponseEntity.ok(MiniProgramLoginResponse.builder().token(token).userId(baseAccount.getUserId()).build());
    }

    /**
     * 管理员登录
     *
     * @param adminId  管理员ID
     * @param password 管理员密码
     * @return token
     */
    @SneakyThrows
    @PostMapping("/admin")
    public ResponseEntity<AdminLoginResponse> adminLogin(@RequestParam(value = "adminId") String adminId,
                                                         @RequestParam(value = "password") String password) {
        log.info("管理员登录请求");
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
        BaseAdmin adminOptional = baseAdminMapper.selectById(adminId);
        if (adminOptional != null && !adminOptional.getDeleteMark() && adminOptional.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            Map<String, String> claims = new HashMap<>(2);
            claims.put("admin_id", adminOptional.getAdminId());
            String token = JwtUtil.createToken(claims);
            log.info("生成token: " + token);
            authRedisTemplate.opsForValue().set("admin@" + adminId, token);
            return ResponseEntity.ok(AdminLoginResponse.builder().token(token).adminType(adminOptional.getAdminType()).build());
        } else {
            log.info("账号或者密码错误");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

}

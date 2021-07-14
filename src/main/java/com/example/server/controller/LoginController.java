package com.example.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.entity.BaseAccount;
import com.example.server.mapper.BaseAccountMapper;
import com.example.server.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@RestController
@Slf4j
public class LoginController {

    final String MINI_LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session";
    final String APPID = "wxdc77479836a295c3";
    final String SECRET = "77f8448883bfe693fcd05c8188e3e62e";

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("docker", '2');
    }};

    private final BaseAccountMapper baseAccountMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public LoginController(BaseAccountMapper baseAccountMapper, RestTemplate restTemplate) {
        this.baseAccountMapper = baseAccountMapper;
        this.restTemplate = restTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("mini/{userType}/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "code") String code,
                                                     @RequestBody Map<String, String> rawData,
                                                     @PathVariable String userType) throws Exception {
        // 请求openid和sessionKey
        log.info("微信小程序登录请求");
        String uri = UriComponentsBuilder
                .fromUriString(MINI_LOGIN_API)
                .queryParam("appid", APPID)
                .queryParam("secret", SECRET)
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
                    .nickName(rawData.get("nickName"))
                    .avatarUrl(rawData.get("avatarUrl"))
                    .userType(userTypeMap.get(userType))
                    .miniOpenId(openid)
                    .phoneNo(rawData.get("phoneNo"))
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
        Map<String, Object> result = new HashMap<>(2);
        result.put("token", token);
        result.put("userId", baseAccount.getUserId());
        return ResponseEntity.ok(result);
    }

}

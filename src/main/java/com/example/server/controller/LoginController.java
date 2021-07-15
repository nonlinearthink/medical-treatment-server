package com.example.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dto.MiniProgramLoginRawData;
import com.example.server.dto.MiniProgramLoginResponse;
import com.example.server.entity.BaseAccount;
import com.example.server.mapper.BaseAccountMapper;
import com.example.server.util.JwtUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
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
    final String APPID = "wxdc77479836a295c3";
    final String SECRET = "77f8448883bfe693fcd05c8188e3e62e";

    final Map<String, Character> userTypeMap = new HashMap<String, Character>(2) {{
        put("user", '1');
        put("docker", '2');
    }};

    private final BaseAccountMapper baseAccountMapper;
    private final RestTemplate restTemplate;
    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    @Autowired
    public LoginController(BaseAccountMapper baseAccountMapper, RestTemplate restTemplate) {
        this.baseAccountMapper = baseAccountMapper;
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
    public ResponseEntity<MiniProgramLoginResponse> login(@RequestParam(value = "code") String code,
                                                          @RequestBody MiniProgramLoginRawData rawData,
                                                          @PathVariable String userType) {
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
        authRedisTemplate.opsForValue().set(token, baseAccount.getUserId().toString());
        return ResponseEntity.ok(MiniProgramLoginResponse.builder().token(token).userId(baseAccount.getUserId()).build());
    }

}

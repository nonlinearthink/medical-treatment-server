package com.example.server;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * @author nonlinearthink
 */
@EnableScheduling
@SpringBootApplication
@Slf4j
public class ServerApplication {

    @Value("${mini-program.user.appid}")
    private String APPID_USER;

    @Value("${mini-program.user.secret}")
    private String SECRET_USER;

    @Value("${mini-program.doctor.appid}")
    private String APPID_DOCTOR;

    @Value("${mini-program.doctor.secret}")
    private String SECRET_DOCTOR;

    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Scheduled(fixedDelay = 7200 * 1000)
    public void fixedDelay() throws InterruptedException {
        log.info("请求新的accessToken");
        String result =
                HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID_USER +
                        "&secret=" + SECRET_USER);
        JSONObject jsonObject = JSONObject.parseObject(result);
        authRedisTemplate.opsForValue().set("access_token@user", jsonObject.get("access_token").toString());
        log.info("access_token@user: " + jsonObject.get("access_token").toString());
        result =
                HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID_DOCTOR +
                        "&secret=" + SECRET_DOCTOR);
        jsonObject = JSONObject.parseObject(result);
        authRedisTemplate.opsForValue().set("access_token@doctor", jsonObject.get("access_token").toString());
        log.info("access_token@doctor: " + jsonObject.get("access_token").toString());
    }

}

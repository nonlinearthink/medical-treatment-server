package com.example.server.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.server.dto.DoctorMessage;
import com.example.server.dto.UserMessage;
import com.example.server.entity.BaseAccount;
import com.example.server.mapper.BaseAccountMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * 消息发送业务
 *
 * @author nonlinearthink
 */
@RestController
@Slf4j
@RequestMapping("/api/message/to")
public class MessageController {

    @Resource(name = "authRedisTemplate")
    private StringRedisTemplate authRedisTemplate;

    private final BaseAccountMapper baseAccountMapper;

    public MessageController(BaseAccountMapper baseAccountMapper) {
        this.baseAccountMapper = baseAccountMapper;
    }

    /**
     * 向用户端发送消息
     *
     * @param message 消息内容
     * @param userId  发送给谁
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/user/{userId}")
    public ResponseEntity<String> sendMessageToUser(@RequestBody UserMessage message,
                                                    @PathVariable(name = "userId") Integer userId) {
        BaseAccount account = baseAccountMapper.selectById(userId);
        JSONObject body = new JSONObject();
        body.put("touser", account.getMiniOpenId());
        body.put("template_id", "MEdQ9SgCqdqH-XGZ9HxEgjuvzJZoJsRZPWZx6sWZ3Ac");
        body.put("page","pages/drugRecords/drugRecords");
        body.put("miniprogram_state", "developer");
        JSONObject json = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        JSONObject date2 = new JSONObject();
        date2.put("value", df.format(message.getTime()));
        json.put("date2", date2);
        JSONObject thing4 = new JSONObject();
        thing4.put("value", message.getContent());
        json.put("thing4", thing4);
        JSONObject phrase5 = new JSONObject();
        phrase5.put("value", message.getStatus());
        json.put("phrase5", phrase5);
        JSONObject thing6 = new JSONObject();
        thing6.put("value", message.getDoctor());
        json.put("thing6", thing6);
        body.put("data", json);
        String accessToken = authRedisTemplate.opsForValue().get("access_token@user");
        String result =
                HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken,
                body.toString());
        return ResponseEntity.ok("ok");
    }

    /**
     * 向用户端发送消息
     *
     * @param message 消息内容
     * @param userId  发送给谁
     * @return 成功或者失败信息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/doctor/{userId}")
    public ResponseEntity<String> sendMessageToDoctor(@RequestBody DoctorMessage message,
                                                      @PathVariable(name = "userId") Integer userId) {
        BaseAccount account = baseAccountMapper.selectById(userId);
        JSONObject body = new JSONObject();
        body.put("toUser", account.getMiniOpenId());
        body.put("template_id", "EV8H08vb9eQVlVFLSwGueIJPM6n-G2kqgzvuOWc3elg");
        JSONObject json = new JSONObject();
        json.put("name1", new JSONObject().put("value", message.getPatient()));
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        json.put("time2", new JSONObject().put("value", df.format(message.getTime())));
        json.put("thing4", new JSONObject().put("value", message.getDept()));
        json.put("thing6", new JSONObject().put("value", message.getDisease()));
        body.put("data", json);
        String accessToken = authRedisTemplate.opsForValue().get("access_token@doctor");
        HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken,
                body.toString());
        return ResponseEntity.ok("ok");
    }

}

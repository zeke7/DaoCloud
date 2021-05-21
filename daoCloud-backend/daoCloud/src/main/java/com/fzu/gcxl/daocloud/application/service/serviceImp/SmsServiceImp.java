package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.infrastructure.util.Base64ConvertUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.support.incrementer.HanaSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImp implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 短信应用 SDK AppID
    int appid = 1400509662; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    String appkey = "ab9724b99a67206a5864ae9cb9beb69c";
    // 短信模板 ID，需要在短信应用中申请
    int templateId = 930335; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    String smsSign = "爱曲爱斯的摸鱼教室"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

    @Override
    public BaseResponse sendSms(@RequestBody JSONObject usersms) {
        String phone = usersms.getString("userphone");
        String type = usersms.getString("type");

        String vcode = redisTemplate.opsForValue().get(phone+type);
        System.out.println("获取到的data:"+phone+type);
        System.out.println(vcode);
        if (!StringUtils.isEmpty(vcode)){
            return new BaseResponse(200, "验证码已存在，还未过期", "");
        }else {
            try {
                String[] phoneNumbers = {phone};
                String str="0123456789";
                StringBuilder verifycode=new StringBuilder(4);
                for(int i=0;i<4;i++)
                {
                    char ch=str.charAt(new Random().nextInt(str.length()));
                    verifycode.append(ch);
                }
                String[] params = { verifycode.toString(), "5"};
                SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
                SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                        templateId, params, smsSign, "", "");
                // {"result":0,"errmsg":"OK","ext":"","sid":"2019:5422519775373183868","fee":1,"isocode":"CN"}
                if (result.result == 0){
                    redisTemplate.opsForValue().set(phone+type, verifycode.toString(), 600*60, TimeUnit.SECONDS);
                    System.out.println(result);
                    return new BaseResponse(result.result, result.errMsg, "");
                }else {
                    return new BaseResponse(result.result, result.errMsg, "");
                }
            } catch (HTTPException e) {
                // HTTP 响应码错误
                e.printStackTrace();
            } catch (JSONException e) {
                // JSON 解析错误
                e.printStackTrace();
            } catch (IOException e) {
                // 网络 IO 错误
                e.printStackTrace();
            }
            return new BaseResponse(500, "发送失败", "");
        }
    }

    @Override
    public BaseResponse verifySms(@RequestBody JSONObject userverified) {
        String phone = userverified.getString("userphone");
        String codefromuser = userverified.getString("codefromuser");
        String type = userverified.getString("type");

        // type: "L0""S1""R2" -> "登陆""注册""忘记密码"
        String vcode = redisTemplate.opsForValue().get(phone+type);
        System.out.println(phone+type);
        System.out.println(vcode);
        // null -> true
        if (!StringUtils.isEmpty(vcode)){
            if (codefromuser.equals(vcode))
                return new BaseResponse(200, "验证码验证成功", "success");
            else
                return new BaseResponse(200, "验证码验证失败", "failed");
        }else {
            return new BaseResponse(200, "验证码不存在，已过期", "codenotexist");
        }
    }
}


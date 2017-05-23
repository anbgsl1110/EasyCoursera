package com.jasonwangex.easyCoursera.callback.web;

import com.jasonwangex.easyCoursera.callback.service.WechatCallbackHandleService;
import com.jasonwangex.easyCoursera.common.bean.Wrapper;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.CacheUtil;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import com.jasonwangex.easyCoursera.utils.LockUtil;
import com.jasonwangex.easyCoursera.utils.WechatUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.XMLConverUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Controller
@RequestMapping("/wechat/callback")
public class WechatCallbackController extends BaseController {
    @Value("${weixin.token}")
    private String token;

    @Resource
    private WechatCallbackHandleService handler;

    private static ExpireKey expireKey = new DefaultExpireKey();

    @RequestMapping(value = "")
    public String check(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestBody String body,
                        @RequestParam(value = "signature", defaultValue = "") String signature,
                        @RequestParam(value = "timestamp", defaultValue = "0") long timestamp,
                        @RequestParam(value = "nonce", defaultValue = "") String nonce,
                        @RequestParam(value = "echostr", defaultValue = "") String echostr) throws IOException {
        Map<String, String> params = new HashMap<>();

        params.put("timestamp", String.valueOf(timestamp));
        params.put("nonce", nonce);
        params.put("token", token);

        CacheUtil.setCache("params", JsonUtil.toString(params));
        CacheUtil.setCache("signature", signature);
        CacheUtil.setCache("echostr", echostr);

        if (!signature.toLowerCase().equals(WechatUtil.sign(params))) return null;

        InputStream inputStream = request.getInputStream();


        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(request.getInputStream(), stringWriter, "UTF-8");
        System.out.println("stringWriter: ========== " + stringWriter.toString());
        System.out.println("body: ========== " + body);


        OutputStream outputStream = response.getOutputStream();

        if (StringUtils.isNotBlank(echostr)) {
            outputStreamWrite(outputStream, echostr);
            return null;
        }

        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();

            CacheUtil.setCache("test", key);
            Wrapper<Boolean> success = new Wrapper<>();
            LockUtil.lock("WECHAT_CALLBACK", () -> {
                if (expireKey.exists(key)) {
                    //重复通知不作处理
                    success.set(false);
                } else {
                    expireKey.add(key);
                    success.set(true);
                }
            });

            if (!success.get()) return null;

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    handler.handle(eventMessage));
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);
            return null;
        }
        return "";
    }

    /**
     * 数据流输出
     *
     * @param outputStream
     * @param text
     * @return
     */
    private boolean outputStreamWrite(OutputStream outputStream, String text) throws UnsupportedEncodingException {
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

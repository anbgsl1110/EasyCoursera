package com.jasonwangex.easyCoursera.callback.web;

import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.CacheUtil;
import com.jasonwangex.easyCoursera.utils.WechatUtil;
import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Controller
@RequestMapping("/wechat/callback")
public class WechatCallbackController extends BaseController {
    private static ExpireKey expireKey = new DefaultExpireKey();

    @RequestMapping(value = "")
    public String check(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "signature", defaultValue = "") String signature,
                        @RequestParam(value = "timestamp", defaultValue = "0") long timestamp,
                        @RequestParam(value = "nonce", defaultValue = "") String nonce,
                        @RequestParam(value = "echostr", defaultValue = "") String echostr) throws IOException {
        Map<String, String> params = new HashMap<>();

        params.put("timestamp", String.valueOf(timestamp));
        params.put("nonce", nonce);
        params.put("echostr", echostr);

        if (!signature.equals(WechatUtil.sign(params))) return null;

        if (StringUtils.isNotBlank(echostr)) return echostr;

        InputStream inputStream = request.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();

            CacheUtil.setCache("test", key);
            if (expireKey.exists(key)) {
                //重复通知不作处理
                return null;
            } else {
                expireKey.add(key);
            }

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "你好");
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);
            return null;
        }
        return "";
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

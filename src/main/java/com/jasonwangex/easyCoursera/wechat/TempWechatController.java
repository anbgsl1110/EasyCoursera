package com.jasonwangex.easyCoursera.wechat;

import com.jasonwangex.easyCoursera.utils.WechatUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/3/2.
 */
@RestController
@RequestMapping("")
public class TempWechatController {
    @RequestMapping("")
    public String check(@RequestParam(value = "signature", defaultValue = "") String signature,
                        @RequestParam(value = "timestamp", defaultValue = "0") long timestamp,
                        @RequestParam(value = "nonce", defaultValue = "") String nonce,
                        @RequestParam(value = "echostr", defaultValue = "") String echostr) {
        Map<String, String> params = new HashMap<>();

        params.put("timestamp", String.valueOf(timestamp));
        params.put("nonce", nonce);
        params.put("echostr", echostr);

        if (signature.equals(WechatUtil.sign(params))) return echostr;

        return null;
    }
}

package com.jasonwangex.easyCoursera.callback.web;

import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Controller
@RequestMapping("/wechat/callback")
public class WechatCallbackController extends BaseController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String check(@RequestParam(value = "signature", defaultValue = "") String signature,
                        @RequestParam(value = "timestamp", defaultValue = "0") long timestamp,
                        @RequestParam(value = "nonce", defaultValue = "") String nonce,
                        @RequestParam(value = "echostr", defaultValue = "") String echostr) {
        Map<String, String> params = new HashMap<>();

        params.put("timestamp", String.valueOf(timestamp));
        params.put("nonce", nonce);
        params.put("echostr", echostr);

        if (!signature.equals(WechatUtil.sign(params))) return null;

        return echostr;
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

package com.jasonwangex.easyCoursera.wechat.web;

import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjz
 * on 17/3/2.
 */
@RestController
@RequestMapping("/wechat/token/check")
public class WechatTokenController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ECResponse check(){
        return ECResponse.success();
    }
}

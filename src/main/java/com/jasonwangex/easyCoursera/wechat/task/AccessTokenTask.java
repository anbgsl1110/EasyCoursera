package com.jasonwangex.easyCoursera.wechat.task;

import com.jasonwangex.easyCoursera.utils.WechatUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * access_token 刷新逻辑
 * Created by wangjz
 * on 17/3/3.
 */
@Component
public class AccessTokenTask {

    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 1000)
    public void refresh(){
        WechatUtil.refreshAccessToken();
    }
}

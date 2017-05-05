package com.jasonwangex.easyCoursera.common.util;

import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.springframework.stereotype.Component;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.qrcode.QrcodeTicket;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Component
public class QrcodeUtil {

    /**
     * 生成一张临时二维码
     *
     * @param sceneId 二维码 sceneId
     * @param ttl     二维码有效时间，单位秒
     * @return
     */
    public static String getTempQrcode(int sceneId, int ttl) {
       QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateTemp(WechatClient.getAccessToken(),
               ttl, sceneId);
       return qrcodeTicket.getUrl();
    }

    /**
     * 生成一张永久二维码
     *
     * @param sceneId 二维码 sceneId
     * @return
     */
    public static String getPermanentQrcode(int sceneId) {
        QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateFinal(WechatClient.getAccessToken(), sceneId);
        return qrcodeTicket.getUrl();
    }
}

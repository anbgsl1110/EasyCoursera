package com.jasonwangex.easyCoursera.callback.service;


import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.qrcode.dao.QrcodeDao;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.qrcode.service.QrcodeService;
import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.User;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Service
public class WechatCallbackHandleService {
    @Resource
    private EcUserDao ecUserDao;
    @Resource
    private EcUserService ecUserService;
    @Resource
    private QrcodeDao qrcodeDao;
    @Resource
    private QrcodeScanHandler qrcodeScanHandler;


    public String handle(EventMessage eventMessage) {
        EcUser user = beforeHandle(eventMessage);

        String event = eventMessage.getEvent();
        if (event.equalsIgnoreCase("subscribe")) {
            if (eventMessage.getEventKey().startsWith("qrscene_")) {
                return handleForQrcode(NumberUtils.toInt(eventMessage.getEventKey().replace("qrscene_", "")), user);
            }
        } else if (event.equalsIgnoreCase("SCAN")) {
            return handleForQrcode(NumberUtils.toInt(eventMessage.getEventKey()), user);
        }

        return "";
    }


    private String handleForQrcode(int sceneId, EcUser user) {
        Qrcode qrcode = qrcodeDao.getByField("scene_id", sceneId);
        if (qrcode == null) return "系统繁忙，请稍后再试";

        switch (qrcode.getObjType()) {
            case EcConsts.Qrcode.TYPE_EXAM:
                return qrcodeScanHandler.handleForExam(qrcode, user);
            case EcConsts.Qrcode.TYPE_TAG:
                return qrcodeScanHandler.handleForTag(qrcode, user);
            case EcConsts.Qrcode.TYPE_TAG_EXAM:
                return qrcodeScanHandler.handleForTagExam(qrcode, user);
            case EcConsts.Qrcode.TYPE_COURSE:
                return qrcodeScanHandler.handleForCourse(qrcode, user);
        }
        return "";
    }

    private EcUser beforeHandle(EventMessage eventMessage) {
        String openId = eventMessage.getFromUserName();
        EcUser user = ecUserDao.getByOpenId(openId);

        if (user == null) {
            User wechatUser = UserAPI.userInfo(WechatClient.getAccessToken(), openId);
            user = ecUserService.create(wechatUser, false, "");
        }

        return user;
    }
}

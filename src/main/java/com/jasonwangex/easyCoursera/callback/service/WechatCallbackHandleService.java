package com.jasonwangex.easyCoursera.callback.service;


import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.common.bean.Wrapper;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.qrcode.dao.QrcodeDao;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import com.jasonwangex.easyCoursera.utils.LockUtil;
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
    @Resource
    private TextMessageHandler textMessageHandler;
    @Resource
    private CallbackContextHelper contextHelper;

    public String handle(EventMessage eventMessage) {
        System.out.println(JsonUtil.toString(eventMessage));
        EcUser user = beforeHandle(eventMessage);

        String event = eventMessage.getEvent();
        switch (eventMessage.getMsgType().toUpperCase()) {
            case "EVENT":
                if (event.equalsIgnoreCase("subscribe")) {
                    if (eventMessage.getEventKey().startsWith("qrscene_")) {
                        return handleForQrcode(NumberUtils.toInt(eventMessage.getEventKey().replace("qrscene_", "")), user);
                    }
                    return handleForSubscribe(user);
                } else if (event.equalsIgnoreCase("SCAN")) {
                    return handleForQrcode(NumberUtils.toInt(eventMessage.getEventKey()), user);
                }
                break;
            case "TEXT":
                return handleForText(eventMessage.getContent(), user.getId());
            default:
                return "暂不支持本消息类型";
        }

        return "";
    }

    private String handleForSubscribe(EcUser user) {
        String msg = "欢迎关注【简课】";
        if (!user.isNameModified()) {
            msg += "，为帮助老师更好的管理，请先输入您的真实姓名，提交后无法再次修改！";
            contextHelper.put(user.getId(), "MODIFY_NAME");
        }

        return msg;
    }

    private String handleForText(String content, int userId) {
        Wrapper<String> stringWrapper = new Wrapper<>();
        LockUtil.lock("WECHAT_CALLBACK_TEXT_MESSAGE_CONTEXT", () -> {
            stringWrapper.set(contextHelper.get(userId));
            contextHelper.remove(userId);
        });

        String context = stringWrapper.get();
        if (context == null) return "你的反馈我们已收到，感谢~比心~";

        if (context.equals("MODIFY_NAME")) return textMessageHandler.handleForModifyName(userId, content);
        else if (context.startsWith("ANSWER_")) {
            int examId = NumberUtils.toInt(context.replaceFirst("ANSWER_", ""));
            return textMessageHandler.handleForAnswer(userId, examId, content);
        }
        return "";
    }


    private String handleForQrcode(int sceneId, EcUser user) {
        Qrcode qrcode = qrcodeDao.getByField("sceneId", sceneId);
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

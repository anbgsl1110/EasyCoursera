package com.jasonwangex.easyCoursera.message.service;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.message.dao.MessageDao;
import com.jasonwangex.easyCoursera.message.domain.Message;
import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.springframework.stereotype.Service;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.message.TextMessage;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/22.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private EcUserDao userDao;

    @Override
    public Message send(int userId, int target, String content, int type) {
        EcUser targetUser = userDao.getById(target);
        if (targetUser == null) return null;

        Message message = new Message();
        message.setCreator(userId);
        message.setUserId(target);
        message.setType(1);
        message.setContent(content);
        messageDao.save(message);

        new Thread(()-> {
            EcUser user = userDao.getById(userId);
            String sendMessage = user.getNickname() + " 向你发送了一条消息：\n\n" + content;
            MessageAPI.messageCustomSend(WechatClient.getAccessToken(), new TextMessage(targetUser.getOpenid(), sendMessage));
        }).start();
        return message;
    }
}

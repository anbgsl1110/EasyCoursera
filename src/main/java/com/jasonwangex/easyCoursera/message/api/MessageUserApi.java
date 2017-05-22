package com.jasonwangex.easyCoursera.message.api;

import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.message.dao.MessageDao;
import com.jasonwangex.easyCoursera.message.domain.Message;
import com.jasonwangex.easyCoursera.message.service.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/22.
 */
@RestController
@NeedRole(UserRoleEnum.USER)
@RequestMapping("/user/api/message")
public class MessageUserApi extends BaseController {
    @Resource
    private MessageDao messageDao;
    @Resource
    private EcUserService ecUserService;

    @Resource
    private MessageService messageService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ECResponse page(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                           @RequestParam(value = "creator", required = false) Integer creator) {
        EcSession session = EcSessionUtil.getSession();

        PageBean<Message> pageBean = messageDao.getMessagePage(session.getUserId(), creator, page, size);
        if (PageBean.isEmpty(pageBean)) return ECResponse.notExist();

        return ECResponse.pagebean(pageBean);
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public ECResponse read(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        Message message = messageDao.getById(id);
        EcSession session = EcSessionUtil.getSession();

        if (message == null || message.getUserId() != session.getUserId()) return ECResponse.notExist();
        messageDao.updateField("msg_read", 1, id);
        ecUserService.syncMessage(session.getUserId());
        message.setMsgRead(true);
        return ECResponse.items(message);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ECResponse send(@RequestParam(value = "target", required = false, defaultValue = "0") int target,
                           @RequestParam(value = "content", required = false, defaultValue = "") String content,
                           @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
        EcSession session = EcSessionUtil.getSession();

        Message message = messageService.send(session.getUserId(), target, content, type);
        ecUserService.syncMessage(target);
        return ECResponse.items(message);
    }


}


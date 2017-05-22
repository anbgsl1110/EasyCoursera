package com.jasonwangex.easyCoursera.message.service;

import com.jasonwangex.easyCoursera.message.domain.Message;

/**
 * Created by wangjz
 * on 17/5/22.
 */
public interface MessageService {

    Message send(int userId, int target, String content, int type);
}

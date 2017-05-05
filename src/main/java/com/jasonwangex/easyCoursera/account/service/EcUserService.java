package com.jasonwangex.easyCoursera.account.service;


import com.jasonwangex.easyCoursera.account.domain.EcUser;
import weixin.popular.bean.user.User;

/**
 * Created by wangjz
 * on 17/5/1.
 */
public interface EcUserService {

    EcUser create(User ecUser, boolean login, String userIp);

    EcUser changeRole(int userId, int roldId, boolean delete);
}

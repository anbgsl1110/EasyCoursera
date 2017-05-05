package com.jasonwangex.easyCoursera.account.service;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.bean.Wrapper;
import com.jasonwangex.easyCoursera.utils.LockUtil;
import org.springframework.stereotype.Service;
import weixin.popular.bean.user.User;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangjz
 * on 17/5/1.
 */
@Service
public class EcUserServiceImpl implements EcUserService {
    @Resource
    private EcUserDao ecUserDao;

    @Override
    public EcUser create(User wechatUser, boolean login, String userIp) {
        if (wechatUser == null) return null;

        EcUser ecUser = ecUserDao.getByOpenId(wechatUser.getOpenid());
        if (ecUser != null) return ecUser;

        ecUser = new EcUser(wechatUser.getOpenid(),
                wechatUser.getNickname(),
                wechatUser.getHeadimgurl(),
                wechatUser.getSex(),
                userIp, new Date());

        ecUserDao.save(ecUser);

        return ecUser;
    }

    @Override
    public EcUser changeRole(int userId, int roleId, boolean delete) {
        Wrapper<EcUser> wrapper = new Wrapper<>();

        LockUtil.lock("EC_CHANGE_ROLE_" + roleId, () -> {
            EcUser ecUser = ecUserDao.getById(userId);

            if (ecUser == null) return;

            if (delete) ecUser.deleteRoleId(roleId);
            else ecUser.addRoleId(roleId);

            ecUserDao.updateField("role_ids", ecUser.getRoleIds(), ecUser.getId());
            wrapper.set(ecUser);
        });

        return wrapper.get();
    }
}

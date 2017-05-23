package com.jasonwangex.easyCoursera.common.task;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Created by wangjz
 * on 17/5/20.
 */
@Component
public class KeepAliveTask {
    @Resource
    private EcUserDao ecUserDao;

    @Scheduled(initialDelay = 2000L, fixedDelay = 10000L)
    public void keepAlive(){
        try {
            if (true) return;
            EcUser user = ecUserDao.getOne(Collections.emptyList(), null);
        } catch (Exception ignore) {

        }
    }
}

package com.jasonwangex.easyCoursera.common.listener;

import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.common.util.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by wangjz
 * on 17/5/11.
 */
@Component
public class EcApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String names[] = applicationContext.getBeanNamesForType(BaseDao.class);
        for (String name : names) {
            BaseDao baseDao = (BaseDao)applicationContext.getBean(name);
            try {
                SpringUtil.add(baseDao.getThisClass(), baseDao);
            } catch (Exception ignore) {
                ;
            }
        }

    }
}

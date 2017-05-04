package com.jasonwangex.easyCoursera.common.web;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/2/24.
 */
@RequestMapping("/healthcheck")
@RestController
public class HealthCheckController extends BaseController {
    @Resource
    private EcUserDao ecUserDao;

    @RequestMapping()
    public String healthCheck() {
        try {
            ecUserDao.getById(1);
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

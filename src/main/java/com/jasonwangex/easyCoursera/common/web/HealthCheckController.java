package com.jasonwangex.easyCoursera.common.web;

import com.jasonwangex.easyCoursera.account.dao.UserDao;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangjz
 * on 17/2/24.
 */
@RequestMapping("/healthcheck")
@RestController
public class HealthCheckController extends BaseController {
    @Resource
    private UserDao userDao;

    @RequestMapping()
    @NeedRole
    public String healthCheck() {
        try {
            userDao.getById(1);
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @Override
    public boolean allow(HttpServletRequest request) {
        EcSession ecSession = EcSessionUtil.getSession(request);
        return ecSession.hasRole(UserRoleEnum.USER);
    }
}

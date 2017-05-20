package com.jasonwangex.easyCoursera.account.api;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangjz
 * on 17/5/20.
 */
@RestController
@RequestMapping("/user/api/account")
public class UserAccountApi extends BaseController {
    @Resource
    private EcUserDao ecUserDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ECResponse getAccount(HttpServletRequest request) {
        EcSession session = EcSessionUtil.getSession(request);
        EcUser user = ecUserDao.getById(session.getUserId());
        return ECResponse.items(user);
    }
}

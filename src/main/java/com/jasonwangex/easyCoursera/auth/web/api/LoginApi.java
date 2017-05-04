package com.jasonwangex.easyCoursera.auth.web.api;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.utils.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@RestController
@RequestMapping("/login")
public class LoginApi {
    @Resource
    private EcUserDao ecUserDao;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public ECResponse check(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "token", required = false) String token) {
        if (StringUtils.isBlank(token)) return ECResponse.fail("error");

        EcSession ecSession = CacheUtil.getCache("EC_LOGIN_" + token);
        if (ecSession == null) return ECResponse.fail("stop");

        if (!ecSession.isLogin()) return ECResponse.fail("checking");

        EcSessionUtil.setSession(request, response, ecSession);
        return ECResponse.success();
    }

    @RequestMapping(value = "/refresh/session", method = RequestMethod.POST)
    public ECResponse refreshSession(HttpServletRequest request,
                                     HttpServletResponse response) {
        EcSession session = EcSessionUtil.getSession(request);
        if (!session.isLogin()) return ECResponse.success();

        EcUser ecUser = ecUserDao.getById(session.getUserId());
        session.setOpenId(ecUser.getOpenid());
        session.setRoleIds(ecUser.getRoleIds());
        session.setSign(EcSessionUtil.getSign(session));

        EcSessionUtil.setSession(request, response, session);

        return ECResponse.success();
    }


}

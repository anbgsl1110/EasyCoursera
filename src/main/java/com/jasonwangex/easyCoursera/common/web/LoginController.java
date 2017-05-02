package com.jasonwangex.easyCoursera.common.web;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.utils.ServerUtil;
import com.jasonwangex.easyCoursera.utils.WebUtil;
import com.jasonwangex.easyCoursera.utils.WechatUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/4/21.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Resource
    private EcUserDao ecUserDao;
    @Resource
    private EcUserService ecUserService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap modelMap,
                        @RequestParam(value = "code", required = false) String code){
        if (!WebUtil.isFromWechat(request)) {
            WebUtil.error403(response);
            return null;
        }

        if (StringUtils.isEmpty(code)) {
            String redirectUrl = SnsAPI.connectOauth2Authorize(WechatUtil.getAppId(), ServerUtil.getUrl("/login"), false, null);
            WebUtil.sendRedirect(response, redirectUrl);
            return null;
        }

        SnsToken snsToken = SnsAPI.oauth2AccessToken(WechatUtil.getAppId(), WechatUtil.getAppSignKey(), code);
        if (snsToken == null || StringUtils.isEmpty(snsToken.getOpenid())) return "403";

        EcUser ecUser = ecUserDao.getByOpenId(snsToken.getOpenid());
        if (ecUser == null) {
            User wechatUser = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
            if (wechatUser == null || StringUtils.isNotBlank(wechatUser.getErrcode())) {
                String redirectUrl = SnsAPI.connectOauth2Authorize(WechatUtil.getAppId(), ServerUtil.getUrl("/login"), true, null);
                WebUtil.sendRedirect(response, redirectUrl);
                return null;
            }
            ecUser = ecUserService.create(wechatUser, true, WebUtil.getIp(request));

            EcSession ecSession = new EcSession();
            ecSession.setUserId(ecUser.getId());
            ecSession.setOpenId(ecUser.getOpenid());
            ecSession.setTimestamp(System.currentTimeMillis());
            ecSession.setNonce(RandomStringUtils.random(32, true, true));
            ecSession.setSign(EcSessionUtil.getSign(ecSession));

            EcSessionUtil.setSession(request, response, ecSession);
        }

        return "common/index";
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ECResponse logout(HttpServletRequest request,
                                     HttpServletResponse response) {
        EcSessionUtil.deleteCookie(request, response);
        return ECResponse.success();
    }

}

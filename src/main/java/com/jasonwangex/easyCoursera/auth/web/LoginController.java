package com.jasonwangex.easyCoursera.auth.web;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.CacheUtil;
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
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @RequestMapping(value = "")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap modelMap,
                        @RequestParam(value = "redirect", required = false) String encodeRedirect,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "code", required = false) String code) {

        if (encodeRedirect == null) encodeRedirect = request.getAttribute("redirect") == null ?
                ServerUtil.getWebRoot() + "/index" : (String) request.getAttribute("redirect");

        String redirect = WebUtil.decodeUrl(encodeRedirect);
        EcSession ecSession = EcSessionUtil.getSession(request);
        if (ecSession.isLogin()) {
            CacheUtil.setCache("EC_LOGIN_" + token, ecSession, 60);
            return "forward:" + redirect;
        }

        if (!WebUtil.isFromWechat(request)) {
            token = UUID.randomUUID().toString();
            modelMap.addAttribute("token", token);
            modelMap.addAttribute("qrcodeContent", ServerUtil.getWebRoot() + "/login?token=" + token + "&redirect=" + encodeRedirect);
            modelMap.addAttribute("redirectUrl", redirect);
            modelMap.addAttribute("loginUrl", encodeRedirect);
            CacheUtil.setCache("EC_LOGIN_" + token, new EcSession(), 5 * 60);
            return "/common/login";
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
        }

        ecSession = new EcSession();
        ecSession.setUserId(ecUser.getId());
        ecSession.setOpenId(ecUser.getOpenid());
        ecSession.setTimestamp(System.currentTimeMillis());
        ecSession.setNonce(RandomStringUtils.random(32, true, true));
        ecSession.setSign(EcSessionUtil.getSign(ecSession));

        EcSessionUtil.setSession(request, response, ecSession);
        CacheUtil.setCache("EC_LOGIN_" + token, ecSession, 60);
        return "redirect:" + ServerUtil.getUrl(redirect);
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

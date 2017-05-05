package com.jasonwangex.easyCoursera.auth.web;

import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController{

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        EcSessionUtil.deleteCookie(request, response);
        return "/common/logout";
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}

package com.jasonwangex.easyCoursera.admin.web;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController extends BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/admin/index";
    }

    @Override
    public boolean allow(EcSession session) {
        return session.hasRole(UserRoleEnum.ADMIN);
    }
}

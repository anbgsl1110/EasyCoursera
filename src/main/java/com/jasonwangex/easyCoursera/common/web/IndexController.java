package com.jasonwangex.easyCoursera.common.web;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController{

    @RequestMapping(value = {"/index", ""}, method = RequestMethod.GET)
    public String index(ModelMap modelMap,
                        HttpServletRequest request) {
        EcSession session = EcSessionUtil.getSession(request);

        modelMap.put("title", "简课-首页");
        modelMap.put("userName", session.getNickname());
        modelMap.put("userAvatar", session.getAvatar());
        return "index/main";
    }

}

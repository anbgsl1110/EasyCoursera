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
public class WebViewController extends BaseController {

    @RequestMapping(value = {"/index", ""}, method = RequestMethod.GET)
    public String index(ModelMap modelMap,
                        HttpServletRequest request) {
        setCommon(modelMap, request, "简课-首页", "index");
        return "teacher/main";
    }

    @RequestMapping(value = {"/checked"}, method = RequestMethod.GET)
    public String checked(ModelMap modelMap,
                          HttpServletRequest request) {
        return "common/checked";
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public String course(ModelMap modelMap,
                         HttpServletRequest request) {
        setCommon(modelMap, request, "简课-课程管理", "course");

        return "teacher/course";
    }

    @RequestMapping(value = "/examination", method = RequestMethod.GET)
    public String exam(ModelMap modelMap,
                       HttpServletRequest request) {
        setCommon(modelMap, request, "简课-题库管理", "examination");

        return "teacher/examination";
    }

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public String tag(ModelMap modelMap,
                      HttpServletRequest request) {
        setCommon(modelMap, request, "简课-知识点管理", "tag");

        return "teacher/tag";
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String qrcode(ModelMap modelMap,
                         HttpServletRequest request) {
        setCommon(modelMap, request, "简课-二维码管理", "qrcode");

        modelMap.put("listUrl", "/user/api/qrcode/page");
        return "teacher/qrcode";
    }


    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String message(ModelMap modelMap,
                          HttpServletRequest request) {
        setCommon(modelMap, request, "简课-消息管理", "message");

        modelMap.put("listUrl", "/user/api/message/page");
        return "teacher/message";
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public String answer(ModelMap modelMap,
                         HttpServletRequest request) {
        setCommon(modelMap, request, "简课-答题批阅", "answer");

        modelMap.put("listUrl", "/user/api/answer/page");
        return "teacher/answer";
    }


    private void setCommon(ModelMap modelMap,
                           HttpServletRequest request,
                           String title,
                           String type) {
        EcSession session = EcSessionUtil.getSession(request);

        modelMap.put("title", title);
        modelMap.put("userId", session.getUserId());
        modelMap.put("userName", session.getNickname());
        modelMap.put("userAvatar", session.getAvatar());
        modelMap.put("viewType", type);
        modelMap.put("listUrl", "/user/api/" + type + "/list");
    }

}

package com.jasonwangex.easyCoursera.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController{

    @RequestMapping(value = {"/index", ""}, method = RequestMethod.GET)
    public String index() {
        return "index/main";
    }

}

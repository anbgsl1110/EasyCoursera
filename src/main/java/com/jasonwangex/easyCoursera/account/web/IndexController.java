package com.jasonwangex.easyCoursera.account.web;

import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        return "/common/index";
    }

}

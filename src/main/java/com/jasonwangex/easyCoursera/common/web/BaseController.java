package com.jasonwangex.easyCoursera.common.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public class BaseController {

    public boolean checkLogin() {
        return true;
    }

    public boolean allow(HttpServletRequest request){
        return true;
    }
}

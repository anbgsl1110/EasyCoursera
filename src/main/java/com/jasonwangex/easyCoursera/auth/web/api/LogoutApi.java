package com.jasonwangex.easyCoursera.auth.web.api;

import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/5/4.
 */
@RestController
@RequestMapping("/logout")
public class LogoutApi {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ECResponse logout(HttpServletRequest request,
                             HttpServletResponse response) {
        EcSessionUtil.deleteCookie(request, response);
        return ECResponse.success();
    }
}

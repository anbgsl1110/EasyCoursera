package com.jasonwangex.easyCoursera.auth.intercepter;

import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.ServerUtil;
import com.jasonwangex.easyCoursera.utils.WebUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/4/23.
 */
public class EcAuthMethodInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return false;

        EcSession session = EcSessionUtil.getSession(request);
        request.setAttribute(EcSessionUtil.EC_SESSION, session);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedRole needRole = handlerMethod.getMethodAnnotation(NeedRole.class);

        if (needRole != null) {
            UserRoleEnum[] roleEnums = needRole.value();

            for (UserRoleEnum roleEnum : roleEnums) {
                if (session.hasRole(roleEnum)) return true;
            }

            if (!session.isLogin()) response.sendRedirect(ServerUtil.getUrl("/login"));
        }

        Object controller = handlerMethod.getBean();
        if (controller instanceof BaseController){
            final BaseController baseController = (BaseController) controller;

            if (baseController.checkLogin() && !session.isLogin() ) {
                response.sendRedirect("/login");
                return false;
            }

            if (baseController.allow(request)) return true;
        }

        response.sendError(403);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

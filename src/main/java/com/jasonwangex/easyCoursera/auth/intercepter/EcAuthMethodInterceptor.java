package com.jasonwangex.easyCoursera.auth.intercepter;

import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.ServerUtil;
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
        if (!(handler instanceof HandlerMethod)) return true;

        EcSession session = EcSessionUtil.getSession(request);
        request.setAttribute(EcSessionUtil.EC_SESSION, session);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedRole needRole = handlerMethod.getMethodAnnotation(NeedRole.class);

        if (needRole != null) {
            UserRoleEnum[] roleEnums = needRole.value();

            for (UserRoleEnum roleEnum : roleEnums) {
                if (session.hasRole(roleEnum)) return true;
            }

            if (!session.isLogin()) {
                request.getRequestDispatcher(ServerUtil.getLoginUri(request))
                        .forward(request, response);
                return false;
            }
        }

        Object controller = handlerMethod.getBean();
        if (controller instanceof BaseController) {
            final BaseController baseController = (BaseController) controller;

            needRole = baseController.getClass().getAnnotation(NeedRole.class);

            if (needRole != null) {
                UserRoleEnum[] roleEnums = needRole.value();

                for (UserRoleEnum roleEnum : roleEnums) {
                    if (session.hasRole(roleEnum)) return true;
                }

                if (!session.isLogin()) {
                    request.getRequestDispatcher(ServerUtil.getLoginUri(request))
                            .forward(request, response);
                    return false;
                }
            }

            if (baseController.checkLogin() && !session.isLogin()) {
                request.getRequestDispatcher(ServerUtil.getLoginUri(request))
                        .forward(request, response);
                return false;
            }

            if (baseController.allow(session)) return true;
        }

        // 若没有继承 baseController 不允许通过
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

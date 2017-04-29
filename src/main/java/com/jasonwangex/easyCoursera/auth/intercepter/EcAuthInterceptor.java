package com.jasonwangex.easyCoursera.auth.intercepter;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public class EcAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handler = handler != null && handler instanceof HandlerMethod ?((HandlerMethod)handler).getBean():handler;
        EcSession session = EcSessionUtil.getSession(request);
        request.setAttribute(EcSessionUtil.EC_SESSION, session);
        if (handler instanceof BaseController){
            final BaseController controller = (BaseController) handler;

            if (((BaseController) handler).checkLogin() && !session.isLogin() ) {
                response.sendRedirect("/login");
                return false;
            }

            if (!controller.allow(request)){
                response.sendError(403);
                return false;
            }
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

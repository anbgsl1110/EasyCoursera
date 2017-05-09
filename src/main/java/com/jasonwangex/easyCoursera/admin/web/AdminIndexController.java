package com.jasonwangex.easyCoursera.admin.web;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;


/**
 * 管理员页面
 *
 * Created by wangjz
 * on 17/5/4.
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController extends BaseController {
    @Resource
    private EcUserDao ecUserDao;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/admin/index";
    }

    /**
     * 用户列表 可根据名称模糊匹配
     *
     * @param name
     * @param page
     * @param size
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String role(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                       ModelMap modelMap) {

        if (size > 100) size = 100;

        PageBean<EcUser> pageBean = ecUserDao.getByName(name, page, size);
        modelMap.put("users", pageBean.getItems());

        return "/admin/userlist";
    }

    @Override
    public boolean allow(EcSession session) {
        return session.hasRole(UserRoleEnum.ADMIN);
    }
}

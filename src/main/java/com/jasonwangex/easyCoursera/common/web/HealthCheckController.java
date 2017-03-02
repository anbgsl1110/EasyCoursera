package com.jasonwangex.easyCoursera.common.web;

import com.jasonwangex.easyCoursera.account.dao.UserDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/2/24.
 */
@RequestMapping("/healthcheck")
@RestController
public class HealthCheckController {
    @Resource
    private UserDao userDao;

    @RequestMapping()
    public String test(){
        try {
            userDao.getById(1);
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }
}

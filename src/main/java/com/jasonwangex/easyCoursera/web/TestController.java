package com.jasonwangex.easyCoursera.web;

import com.jasonwangex.easyCoursera.dao.UserDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/2/24.
 */
@RequestMapping("/healthcheck")
@RestController
public class TestController {
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

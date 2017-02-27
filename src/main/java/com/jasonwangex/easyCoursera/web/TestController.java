package com.jasonwangex.easyCoursera.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjz
 * on 17/2/24.
 */
@RequestMapping("/check/health")
@RestController
public class TestController {
    @RequestMapping()
    public String test(){
        return "success";
    }
}

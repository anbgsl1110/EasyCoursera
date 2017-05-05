package com.jasonwangex.easyCoursera.admin.api;

import com.jasonwangex.easyCoursera.account.service.EcUserService;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/5.
 */
@RestController
@RequestMapping("/admin/api/role")
public class AdminRoleApi extends BaseController {
    @Resource
    private EcUserService ecUserService;

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ECResponse changeRole(@RequestParam(value = "user_id", required = false, defaultValue = "0") int userId,
                                 @RequestParam(value = "role_id", required = false, defaultValue = "0") int roleId,
                                 @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted) {
        ecUserService.changeRole(userId, roleId, deleted);
        return ECResponse.success();
    }
}

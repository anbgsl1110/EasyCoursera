package com.jasonwangex.easyCoursera.qrcode.api;

import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.qrcode.dao.QrcodeDao;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.qrcode.service.QrcodeService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@RestController
@RequestMapping("/user/api/qrcode")
@NeedRole(UserRoleEnum.TEACHER)
public class QrcodeUserApi extends BaseController {
    @Resource
    private QrcodeService qrcodeService;
    @Resource
    private QrcodeDao qrcodeDao;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ECResponse generate(@RequestParam(value = "ttl", required = false, defaultValue = "259200") Integer ttl,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "objType", required = false, defaultValue = "0") int objType,
                               @RequestParam(value = "objId", required = false, defaultValue = "0") int objId) {
        Map<String, String> attachMap = new HashMap<>();

        Qrcode qrcode = qrcodeService.create(name, ttl, objType, objId, attachMap);
        if (qrcode == null) return ECResponse.fail("操作失败，请重试");

        return ECResponse.items(qrcode);
    }

    @RequestMapping(value = "/patch", method = RequestMethod.POST)
    public ECResponse patch(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "objType", required = false, defaultValue = "0") int objType,
                            @RequestParam(value = "objId", required = false, defaultValue = "0") int objId) {
        if (id == null || id <= 0) return ECResponse.notExist();

        if (StringUtils.isBlank(name)) return ECResponse.fail(4200, "名称不能为空");

        Qrcode qrcode = qrcodeService.update(id, name, objType, objId);
        if (qrcode == null) return ECResponse.notExist();
        return ECResponse.items(qrcode);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ECResponse refresh(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
                              @RequestParam(value = "ttl", required = false, defaultValue = "259200") int ttl) {

        if (id <= 0 || ttl <= 0)return ECResponse.fail(4200, "参数错误");

        Qrcode qrcode = qrcodeService.refresh(id, ttl);
        if (qrcode == null) return ECResponse.notExist();

        return ECResponse.items(qrcode);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ECResponse page(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                           @RequestParam(value = "objType", required = false) Integer objType,
                           @RequestParam(value = "creator", required = false) Integer creator) {

        List<Criterion> criteria = new ArrayList<>();
        if (objType != null) criteria.add(Restrictions.eq("objType", objType));
        if (creator != null) criteria.add(Restrictions.eq("creator", creator));

        PageBean<Qrcode> pageBean = qrcodeDao.getPage(criteria, null, page, size);
        if (pageBean == null || CollectionUtils.isEmpty(pageBean.getItems())) return ECResponse.notExist();

        return ECResponse.pagebean(pageBean);
    }


}

package com.jasonwangex.easyCoursera.qrcode.api;

import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.qrcode.service.QrcodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@RestController
@RequestMapping("/user/api/qrcode")
public class QrcodeUserApi {
    @Resource
    private QrcodeService qrcodeService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ECResponse generate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "ttl", required = false, defaultValue = "259200") Integer ttl,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "type", required = false) Integer type,
                               @RequestParam(value = "objType", required = false, defaultValue = "0") int objType,
                               @RequestParam(value = "objId", required = false, defaultValue = "0") int objId,
                               @RequestParam(value = "tagId", required = false) Integer tagId) {
        Map<String, String> attachMap = new HashMap<>();

        if (id != null) attachMap.put("id", String.valueOf(id));
        if (type != null) attachMap.put("type", String.valueOf(type));
        if (tagId != null) attachMap.put("tagId", String.valueOf(tagId));

        Qrcode qrcode = qrcodeService.create(name, ttl, objType, objId, attachMap);
        if (qrcode == null) return ECResponse.fail("操作失败，请重试");

        return ECResponse.items(qrcode);
    }
}

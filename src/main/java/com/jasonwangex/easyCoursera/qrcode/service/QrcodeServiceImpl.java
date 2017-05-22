package com.jasonwangex.easyCoursera.qrcode.service;

import com.jasonwangex.easyCoursera.common.util.QrcodeUtil;
import com.jasonwangex.easyCoursera.qrcode.dao.QrcodeDao;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.utils.IdUtil;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@Service
public class QrcodeServiceImpl implements QrcodeService {
    @Resource
    private QrcodeDao qrcodeDao;

    @Override
    public Qrcode create(String name, int ttl, int objType, int objId, Map<String, String> attach) {
        int sceneId = IdUtil.getInt("qrcode_scene");
        String url = QrcodeUtil.getTempQrcode(sceneId, ttl);
        if (StringUtils.isBlank(url)) return null;

        Qrcode qrcode = new Qrcode();
        qrcode.setTemp(true);
        qrcode.setRefreshTime(new Date());
        qrcode.setName(name);
        qrcode.setAttach(JsonUtil.toString(attach));
        qrcode.setUrl(url);
        qrcode.setSceneId(sceneId);
        qrcode.setObjType(objType);
        qrcode.setObjId(objId);
        qrcode.setTtl(ttl);

        qrcodeDao.save(qrcode);
        return qrcode;
    }

    @Override
    public Qrcode update(int id, String name, int objType, int objId) {
        Qrcode qrcode = qrcodeDao.getById(id);
        if (qrcode == null) return null;

        qrcode.setName(name);
        qrcode.setObjId(objId);
        qrcode.setObjType(objType);

        qrcodeDao.save(qrcode);
        return qrcode;
    }

    @Override
    public Qrcode refresh(int id, int ttl) {
        Qrcode qrcode = qrcodeDao.getById(id);

        if (qrcode == null) return null;
        String url = QrcodeUtil.getTempQrcode(qrcode.getSceneId(), ttl);
        qrcode.setUrl(url);
        qrcode.setTtl(ttl);
        qrcode.setRefreshTime(new Date());

        qrcodeDao.save(qrcode);
        return qrcode;
    }
}

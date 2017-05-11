package com.jasonwangex.easyCoursera.qrcode.dao;

import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.common.util.QrcodeUtil;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import com.jasonwangex.easyCoursera.utils.IdUtil;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@Repository
public class QrcodeDaoImpl extends BaseDaoImpl<Qrcode> implements QrcodeDao {

    @Override
    public Qrcode create(String name, int ttl, Map<String, String> attach) {
        int sceneId = IdUtil.getInt("qrcode_scene");
        String url = QrcodeUtil.getTempQrcode(sceneId, ttl);
        if (StringUtils.isBlank(url)) return null;

        Qrcode qrcode = new Qrcode();
        qrcode.setType(EcConsts.Qrcode.TYPE_ANSWER_EXAM);
        qrcode.setTemp(true);
        qrcode.setRefreshTime(new Date());
        qrcode.setName(name);
        qrcode.setAttach(JsonUtil.toString(attach));
        qrcode.setUrl(url);
        qrcode.setSceneId(sceneId);

        save(qrcode);
        return qrcode;
    }
}

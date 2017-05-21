package com.jasonwangex.easyCoursera.qrcode.service;

import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;

import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/21.
 */
public interface QrcodeService {

    Qrcode create(String name, int ttl, int objType, int objId, Map<String, String> attach);
}

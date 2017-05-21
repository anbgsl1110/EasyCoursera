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
}

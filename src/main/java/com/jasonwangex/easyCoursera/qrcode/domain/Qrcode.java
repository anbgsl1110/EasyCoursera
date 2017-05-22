package com.jasonwangex.easyCoursera.qrcode.domain;

import com.jasonwangex.easyCoursera.common.annotation.EcDomain;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@Entity
@DynamicInsert
@EcDomain("qrcode")
@Table(name = "ec_qrcode")
public class Qrcode extends BaseEntity {
    private static final long serialVersionUID = 2020046171356000616L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int sceneId;
    private boolean temp;
    private String attach;
    private String url;
    private Date refreshTime;
    private int objType;
    private int objId;
    private int ttl;

    private Date createTime;
    private Date modifyTime;

    @Transient
    private Map<String, String> attachMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Map<String, String> getAttachMap() {
        if (!CollectionUtils.isEmpty(attachMap)) return attachMap;

        attachMap = JsonUtil.toObject(Map.class, attach);
        return attachMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}

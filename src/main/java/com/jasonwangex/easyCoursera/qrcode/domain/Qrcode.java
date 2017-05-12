package com.jasonwangex.easyCoursera.qrcode.domain;

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
@Table(name = "ec_qrcode")
public class Qrcode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int type;
    private int sceneId;
    private boolean temp;
    private String attach;
    private String url;
    private Date refreshTime;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}

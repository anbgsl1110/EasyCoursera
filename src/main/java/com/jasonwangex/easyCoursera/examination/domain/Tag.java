package com.jasonwangex.easyCoursera.examination.domain;

import com.jasonwangex.easyCoursera.common.annotation.EcDomain;
import com.jasonwangex.easyCoursera.common.domain.AuthorEntity;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangjz
 * on 17/5/9.
 */
@Entity
@DynamicInsert
@EcDomain("tag")
@Table(name = "ec_tag")
public class Tag extends BaseEntity implements AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int root;
    private String name;
    private String detail;
    private int creator;
    private int modifier;
    private Date createTime;
    private Date modifyTime;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int getCreator() {
        return creator;
    }

    @Override
    public void setCreator(int creator) {
        this.creator = creator;
    }

    @Override
    public int getModifier() {
        return modifier;
    }

    @Override
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getModifyTime() {
        return modifyTime;
    }

    @Override
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

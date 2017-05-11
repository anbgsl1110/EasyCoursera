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
@EcDomain("examination")
@Table(name = "ec_examination")
public class Examination extends BaseEntity implements AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private int type;
    private String answer;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getModifier() {
        return modifier;
    }

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

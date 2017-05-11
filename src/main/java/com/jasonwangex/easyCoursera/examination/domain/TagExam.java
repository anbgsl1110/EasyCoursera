package com.jasonwangex.easyCoursera.examination.domain;

import com.jasonwangex.easyCoursera.common.annotation.EcDomain;
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
@EcDomain("tagexam")
@Table(name = "ec_tag_exam")
public class TagExam extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int examId;
    private int tagId;
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

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
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

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}

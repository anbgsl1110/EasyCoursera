package com.jasonwangex.easyCoursera.course.domain;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.annotation.EcDomain;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Entity
@DynamicInsert
@EcDomain("courseChoose")
@Table(name = "ec_course_choose")
public class CourseChoose extends BaseEntity {
    private static final long serialVersionUID = 7280078247379755746L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int userId;
    private int courseId;
    private boolean checked;
    private Date createTime;
    private Date modifyTime;

    @Transient
    private Course course;
    @Transient
    private EcUser user;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

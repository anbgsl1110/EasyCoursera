package com.jasonwangex.easyCoursera.course.service;

import com.jasonwangex.easyCoursera.course.dao.CourseChooseDao;
import com.jasonwangex.easyCoursera.course.domain.CourseChoose;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Service
public class CourseChooseServiceImpl implements CourseChooseService {
    @Resource
    private CourseChooseDao courseChooseDao;

    @Override
    public CourseChoose choose(boolean needCheck, int courseId, int userId) {
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("courseId", courseId));
        criteria.add(Restrictions.eq("userId", userId));

        CourseChoose courseChoose = courseChooseDao.getOne(criteria);
        if (courseChoose != null) return courseChoose;

        courseChoose = new CourseChoose();
        courseChoose.setCourseId(courseId);
        courseChoose.setUserId(userId);
        courseChoose.setChecked(!needCheck);

        courseChooseDao.save(courseChoose);

        return courseChoose;
    }
}

package com.jasonwangex.easyCoursera.course.api;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.course.dao.CourseChooseDao;
import com.jasonwangex.easyCoursera.course.dao.CourseDao;
import com.jasonwangex.easyCoursera.course.domain.Course;
import com.jasonwangex.easyCoursera.course.domain.CourseChoose;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@RestController
@RequestMapping("/user/api/course/choose")
public class CourseChooseApi {
    @Resource
    private CourseChooseDao courseChooseDao;

    @Resource
    private CourseDao courseDao;

    @Resource
    private EcUserDao ecUserDao;

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ECResponse getAllMyChoose() {
        EcSession session = EcSessionUtil.getSession();

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("userId", session.getUserId()));

        List<CourseChoose> courseChooses = courseChooseDao.getList(criteria);
        Map<Integer, Course> courseMap = courseDao.getListById(courseChooses.stream()
                .map(CourseChoose::getCourseId).collect(Collectors.toList())).stream().
                collect(Collectors.toMap(Course::getId, Function.identity()));

        courseChooses.forEach(courseChoose -> courseChoose.setCourse(courseMap.get(courseChoose.getCourseId())));
        return ECResponse.items(courseChooses);
    }

    @RequestMapping(value = "/chosen/{id}", method = RequestMethod.GET)
    public ECResponse getMyChosen(@PathVariable("id") int courseId) {
        EcSession session = EcSessionUtil.getSession();

        Course course = courseDao.getById(courseId);
        if (course == null || course.getCreator() != session.getUserId()) return ECResponse.forbbiden();

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("courseId", courseId));

        List<CourseChoose> courseChooses = courseChooseDao.getList(criteria);
        Map<Integer, EcUser> userMap = ecUserDao.getListById(courseChooses.stream()
                .map(CourseChoose::getUserId).collect(Collectors.toList())).stream().
                collect(Collectors.toMap(EcUser::getId, Function.identity()));

        courseChooses.forEach(courseChoose -> courseChoose.setUser(userMap.get(courseChoose.getUserId())));
        return ECResponse.items(courseChooses);
    }
}

package com.jasonwangex.easyCoursera.course.service;

import com.jasonwangex.easyCoursera.course.domain.CourseChoose;

/**
 * Created by wangjz
 * on 17/5/23.
 */
public interface CourseChooseService {

    CourseChoose choose(boolean needCheck, int courseId, int userId);
}

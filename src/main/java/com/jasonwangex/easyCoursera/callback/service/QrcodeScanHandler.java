package com.jasonwangex.easyCoursera.callback.service;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.answer.service.AnswerService;
import com.jasonwangex.easyCoursera.course.dao.CourseDao;
import com.jasonwangex.easyCoursera.course.domain.Course;
import com.jasonwangex.easyCoursera.course.domain.CourseChoose;
import com.jasonwangex.easyCoursera.course.service.CourseChooseService;
import com.jasonwangex.easyCoursera.examination.dao.ExaminationDao;
import com.jasonwangex.easyCoursera.examination.dao.TagDao;
import com.jasonwangex.easyCoursera.examination.dao.TagExamDao;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import com.jasonwangex.easyCoursera.examination.domain.Tag;
import com.jasonwangex.easyCoursera.examination.domain.TagExam;
import com.jasonwangex.easyCoursera.qrcode.domain.Qrcode;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Component
public class QrcodeScanHandler {
    @Resource
    private ExaminationDao examinationDao;
    @Resource
    private AnswerDao answerDao;
    @Resource
    private AnswerService answerService;
    @Resource
    private TagDao tagDao;
    @Resource
    private TagExamDao tagExamDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseChooseService courseChooseService;
    @Resource
    private CallbackContextHelper contextHelper;

    public String handleForExam(Qrcode qrcode, EcUser user) {
        int examId = qrcode.getObjId();

        Examination examination = examinationDao.getById(examId);
        if (examination == null) return "题目已不存在，请联系老师";

        Answer answer = answerService.createOrGet(examId, user.getId(), 0);
        if (answer.isClosed() || answer.getAnswerCount() >= 3) return "回答已批阅或已超过答题次数";

        contextHelper.put(user.getId(), "ANSWER_" + examId);
        return "题目如下：\n\n" + examination.getContent();
    }

    public String handleForTag(Qrcode qrcode, EcUser user) {
        int tagId = qrcode.getObjId();

        Tag tag = tagDao.getById(tagId);
        if (tag == null) return "知识点已不存在，请联系老师";

        return tag.getName() + "\n\n" + tag.getDetail();
    }

    public String handleForTagExam(Qrcode qrcode, EcUser user) {
        int tagId = qrcode.getObjId();

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("tagId", tagId));
        criteria.add(Restrictions.eq("closed", false));
        Answer answer = answerDao.getOne(criteria);

        if (answer == null) {
            criteria = new ArrayList<>();
            criteria.add(Restrictions.eq("tagId", tagId));
            int total = tagExamDao.count(criteria);
            if (total == 0) return "该知识点下未绑定任何题目，请联系老师";

            int offset = RandomUtils.nextInt(0, total);

            String sql = "SELECT * from ec_tag_exam where tag_id=? limit 1 offset ?";
            TagExam tagExam = tagExamDao.getOne(sql, tagId, offset);

            answer = answerService.createOrGet(tagExam.getExamId(), user.getId(), tagId);
        }

        contextHelper.put(user.getId(), "ANSWER_" + answer.getExamId());
        Examination examination = examinationDao.getById(answer.getExamId());
        if (examination == null) return "题目已不存在，请联系老师";


        return "题目如下：\n\n" + examination.getContent();
    }

    public String handleForCourse(Qrcode qrcode, EcUser user) {
        int courseId = qrcode.getObjId();

        Course course = courseDao.getById(courseId);
        if (course == null) return "课程不存在，请联系老师";

        CourseChoose courseChoose = courseChooseService.choose(course.isNeedCheck(), courseId, user.getId());

        String message = "你已经成功选入【$COURSE_PLACE_HOLDER】，课程主要内容如下:\n\n$CONTENT_PLACE_HOLDER";
        if (courseChoose.isChecked()) return message.replace("$COURSE_PLACE_HOLDER", course.getName()).replace("$CONTENT_PLACE_HOLDER", course.getContent());

        return "你已经成功提交申请选入【$COURSE_PLACE_HOLDER】，等待老师审核";
    }
}

package com.jasonwangex.easyCoursera.examination.api;

import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.examination.dao.TagDao;
import com.jasonwangex.easyCoursera.examination.dao.TagExamDao;
import com.jasonwangex.easyCoursera.examination.domain.Tag;
import com.jasonwangex.easyCoursera.examination.domain.TagExam;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@RestController
@RequestMapping("/user/api/tagexam")
public class TagExamUserApi extends BaseController{
    @Resource
    private TagExamDao tagExamDao;

    @Resource
    private TagDao tagDao;

    @RequestMapping("/all")
    public ECResponse getAll(@RequestParam(value = "examId", required = false, defaultValue = "0") int examId) {
        if (examId <= 0)return ECResponse.notExist();

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("examId", examId));

        List<TagExam> tagExams = tagExamDao.getList(criteria);
        if (CollectionUtils.isEmpty(tagExams)) return ECResponse.notExist();

        criteria = new ArrayList<>();
        criteria.add(Restrictions.in("id", tagExams.stream().map(TagExam::getTagId).collect(Collectors.toList())));
        List<Tag> tags = tagDao.getList(criteria);
        return ECResponse.items(tags);
    }
}

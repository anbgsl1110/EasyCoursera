package com.jasonwangex.easyCoursera.answer.api;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.answer.service.AnswerService;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.examination.dao.ExaminationDao;
import com.jasonwangex.easyCoursera.message.domain.Message;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@RestController
@NeedRole(UserRoleEnum.TEACHER)
@RequestMapping("/user/api/answer")
public class TeacherAnswerApi extends BaseController{
    @Resource
    private AnswerDao answerDao;

    @Resource
    private EcUserDao ecUserDao;

    @Resource
    private AnswerService answerService;


    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public ECResponse commitAnswer(@RequestParam(value = "reply", required = false) String reply,
                                   @RequestParam(value = "judge", required = false, defaultValue = "true") boolean right,
                                   @RequestParam(value = "id", required = false, defaultValue = "0") int id) {

        Answer answer = answerService.reply(id, reply, right);
        if (answer == null) return ECResponse.notExist();

        return ECResponse.items(answer);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ECResponse page(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        PageBean<Answer> pageBean = answerDao.getPage(0, page, size);

        List<Integer> userIds = pageBean.getItems().stream().map(Answer::getUserId).collect(Collectors.toList());
        List<EcUser> users = ecUserDao.getListById(userIds);

        Map<Integer,String> userIdName = users.stream().collect(Collectors.toMap(EcUser::getId, EcUser::getNickname));
        pageBean.getItems().forEach(answer -> answer.setUserName(userIdName.get(answer.getUserId())));

        return ECResponse.pagebean(pageBean);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ECResponse get(@PathVariable("id") int answerId) {
        Answer answer = answerDao.getById(answerId);
        if (answer == null) return ECResponse.notExist();

        answer.setUserName(ecUserDao.getById(answer.getUserId()).getNickname());

        return ECResponse.items(answer);
    }

}

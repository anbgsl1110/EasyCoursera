package com.jasonwangex.easyCoursera.common.api;

import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.annotation.EcParamForeign;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.bean.ModifyParam;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.bean.ValidationResult;
import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.common.domain.AuthorEntity;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import com.jasonwangex.easyCoursera.common.util.EcClassAliasUtil;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.util.SpringUtil;
import com.jasonwangex.easyCoursera.common.util.ValidationUtils;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.utils.JsonUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wangjz
 * on 17/5/11.
 */
@RestController
@RequestMapping("/user/api/")
@NeedRole(value = UserRoleEnum.USER)
public class BaseUserApi extends BaseController {

    @RequestMapping(value = "/{domain}/create", method = RequestMethod.POST)
    public ECResponse create(@PathVariable String domain, HttpServletRequest request) throws IllegalAccessException, InstantiationException {
        Class<?> paramClazz = EcClassAliasUtil.getClass(EcParamType.CREATE, domain);
        Class<?> entityClazz = EcClassAliasUtil.getEntityClass(domain);
        if (paramClazz == null) return ECResponse.notExist();

        if (!isAccessible(paramClazz, request)) return ECResponse.forbbiden();
        Object param = JsonUtil.toObject(paramClazz, JsonUtil.readParam(request));

        ValidationResult result = ValidationUtils.check(param);
        if (!result.isValid()) return ECResponse.packageError(result);

        Field[] paramFields = paramClazz.getDeclaredFields();
        Field[] entityFields = entityClazz.getDeclaredFields();

        EcParamForeign foreign = paramClazz.getAnnotation(EcParamForeign.class);
        if (foreign != null && !isForeignValid(foreign.target(), paramFields, param)) return ECResponse.notExist();

        Object entity = entityClazz.newInstance();
        setFiled(entityFields, paramFields, entity, param);

        if (entity instanceof AuthorEntity) {
            EcSession session = EcSessionUtil.getSession();
            ((AuthorEntity) entity).setCreator(session.getUserId());
            ((AuthorEntity) entity).setModifier(session.getUserId());
        }

        BaseDao dao = SpringUtil.getDao(entityClazz);
        dao.save((BaseEntity) entity);
        return ECResponse.items(entity);
    }

    @RequestMapping(value = "/{domain}/modify", method = RequestMethod.POST)
    public ECResponse modify(@PathVariable String domain, HttpServletRequest request) throws IllegalAccessException, InstantiationException {
        Class<?> paramClazz = EcClassAliasUtil.getClass(EcParamType.MODIFY, domain);
        Class<?> entityClazz = EcClassAliasUtil.getEntityClass(domain);
        if (paramClazz == null) return ECResponse.notExist();

        if (!isAccessible(paramClazz, request)) return ECResponse.forbbiden();

        ModifyParam param = (ModifyParam) JsonUtil.toObject(paramClazz, JsonUtil.readParam(request));
        ValidationResult result = ValidationUtils.check(param);
        if (!result.isValid()) return ECResponse.packageError(result);

        BaseDao dao = SpringUtil.getDao(entityClazz);
        BaseEntity entity = dao.getById(param.getId());
        if (entity == null) return ECResponse.notExist();

        Field[] paramFields = paramClazz.getDeclaredFields();
        Field[] entityFields = entityClazz.getDeclaredFields();
        EcParamForeign foreign = paramClazz.getAnnotation(EcParamForeign.class);
        if (foreign != null && !isForeignValid(foreign.target(), paramFields, param)) return ECResponse.notExist();

        setFiled(entityFields, paramFields, entity, param);
        if (entity instanceof AuthorEntity) {
            EcSession session = EcSessionUtil.getSession();
            ((AuthorEntity) entity).setModifier(session.getUserId());
        }

        dao.save(entity);
        return ECResponse.items(entity);
    }

    @RequestMapping(value = "/{domain}/delete", method = RequestMethod.POST)
    public ECResponse delete(@PathVariable String domain,
                             @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                             HttpServletRequest request) throws IllegalAccessException, InstantiationException {
        Class<?> paramClazz = EcClassAliasUtil.getClass(EcParamType.MODIFY, domain);
        Class<?> entityClazz = EcClassAliasUtil.getEntityClass(domain);

        if (!isAccessible(paramClazz, request)) return ECResponse.forbbiden();

        if (paramClazz == null) return ECResponse.notExist();

        BaseDao dao = SpringUtil.getDao(entityClazz);
        BaseEntity entity = dao.getById(id);
        if (entity == null) return ECResponse.notExist();

        dao.deleteById(id);
        return ECResponse.success();
    }

    @RequestMapping(value = "/{domain}/get", method = RequestMethod.GET)
    public ECResponse get(@PathVariable String domain,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          HttpServletRequest request) throws IllegalAccessException, InstantiationException {

        Class<?> paramClazz = EcClassAliasUtil.getClass(EcParamType.QUERY, domain);
        Class<?> entityClazz = EcClassAliasUtil.getEntityClass(domain);

        if (!isAccessible(paramClazz, request)) return ECResponse.forbbiden();

        if (paramClazz == null) return ECResponse.notExist();

        BaseDao dao = SpringUtil.getDao(entityClazz);
        BaseEntity entity = dao.getById(id);
        if (entity == null) return ECResponse.notExist();

        return ECResponse.items(entity);
    }

    @RequestMapping(value = "/{domain}/list", method = RequestMethod.GET)
    public ECResponse get(@PathVariable String domain,
                          HttpServletRequest request) throws IllegalAccessException, InstantiationException {

        Class<?> paramClazz = EcClassAliasUtil.getClass(EcParamType.QUERY, domain);
        Class<?> entityClazz = EcClassAliasUtil.getEntityClass(domain);
        if (paramClazz == null) return ECResponse.notExist();

        if (!isAccessible(paramClazz, request)) return ECResponse.forbbiden();

        Object param = JsonUtil.toObject(paramClazz, JsonUtil.readParam(request));
        if (param == null) param = paramClazz.newInstance();

        ValidationResult result = ValidationUtils.check(param);
        if (!result.isValid()) return ECResponse.packageError(result);

        Field[] paramFields = paramClazz.getDeclaredFields();

        List<Criterion> criteria = new ArrayList<>();

        int page = 1;
        int size = 10;
        for (Field paramField : paramFields) {
            paramField.setAccessible(true);

            if (paramField.getName().equals("page")) {
                page = paramField.getInt(param);
                continue;
            }

            if (paramField.getName().equals("size")) {
                size = paramField.getInt(param);
                continue;
            }

            Object paramFieldProperty = paramField.get(param);
            if (paramFieldProperty != null) {
                criteria.add(Restrictions.eq(paramField.getName(), paramFieldProperty));
            }
        }

        BaseDao dao = SpringUtil.getDao(entityClazz);
        PageBean pageBean = dao.getPage(criteria, null, page, size);
        return ECResponse.pagebean(pageBean);
    }


    private void setFiled(Field[] fields, Field[] paramFields, Object entity, Object param) throws IllegalAccessException {
        Map<String, Field> fieldMap = Stream.of(fields)
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .collect(Collectors.toMap(Field::getName, Function.identity()));

        for (Field paramField : paramFields) {
            paramField.setAccessible(true);
            Object paramFieldProperty = paramField.get(param);
            if (paramFieldProperty != null) {
                fieldMap.get(paramField.getName()).set(entity, paramFieldProperty);
            }
        }
    }

    private boolean isAccessible(Class paramClazz, HttpServletRequest request) {
        EcSession ecSession = EcSessionUtil.getSession(request);
        EcParam ecParam = (EcParam) paramClazz.getAnnotation(EcParam.class);
        return ecSession.hasRole(ecParam.role());
    }

    private boolean isForeignValid(Class targetClass, Field[] paramFields, Object param) throws IllegalAccessException {
        Integer id = 0;
        for (Field paramField : paramFields) {
            paramField.setAccessible(true);
            if (paramField.getName().equals("id")) {
                id = paramField.getInt("id");
            }
        }
        BaseDao dao = SpringUtil.getDao(targetClass);
        if (dao == null) return false;

        Object o = dao.getById(id);
        return o != null;
    }
}

package com.jasonwangex.easyCoursera.common.util;


import com.jasonwangex.easyCoursera.common.annotation.EcDomain;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import com.jasonwangex.easyCoursera.common.exception.EcClassAliasClashException;
import com.jasonwangex.easyCoursera.utils.LockUtil;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 为一些特殊 class 注册别名
 * Created by wangjz
 * on 17/5/11.
 */
@Component
public class EcClassAliasUtil {

    private static final Map<String, Class> FACTORY = new HashMap<>();

    @PostConstruct
    public void init() {
        LockUtil.lock("EC_CLASS_ALIAS_UTIL_INIT", () -> {
            if (!FACTORY.isEmpty()) return;
            Reflections reflections = new Reflections();
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(EcParam.class);
            classes.addAll(reflections.getTypesAnnotatedWith(EcDomain.class));
            for (Class<?> clazz : classes) {
                EcParam annotation = clazz.getAnnotation(EcParam.class);
                if (annotation != null) {
                    String alias = annotation.type().getValue() + annotation.value();
                    if (FACTORY.containsKey(alias) && clazz != FACTORY.get(alias)) {
                        throw new EcClassAliasClashException("class alias clash");
                    }
                    FACTORY.put(alias, clazz);
                }

                EcDomain entity = clazz.getAnnotation(EcDomain.class);
                if (entity != null) {
                    String alias = "entity_" + entity.value();
                    if (FACTORY.containsKey(alias) && clazz != FACTORY.get(alias)) {
                        throw new EcClassAliasClashException("class alias clash");
                    }
                    FACTORY.put(alias, clazz);
                }
            }
        });
    }

    public static Class getClass(EcParamType type, String alias) {
        return FACTORY.get(type.getValue() + alias);
    }

    public static Class getEntityClass(String alias) {
        return FACTORY.get("entity_" + alias);
    }
}

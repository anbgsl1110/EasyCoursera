package com.jasonwangex.easyCoursera.examination.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@EcParam(value = "tag", type = EcParamType.QUERY, role = UserRoleEnum.USER)
public class TagQueryParam implements Serializable{
    private static final long serialVersionUID = 7072658508676910666L;

    @Min(1)
    private Integer id;

    @Min(1)
    private Integer root;

    @Length(min = 1)
    private String name;

    @Min(1)
    private Integer creator;

    @Range(min = 1)
    private int page = 1;

    @Range(min = 1, max = 100)
    private int size = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(Integer root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

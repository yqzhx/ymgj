package com.uustop.project.system.personal.domain;

import com.uustop.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 个性化功能选择表 prom_personal
 *
 * @author zx
 * @date 2019-07-18
 */
public class Personal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer personalId;
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 企业id
     */
    private Integer deptId;

    /**
     * 父级id
     */
    private Integer parentId;
    /**
     * 父级名称
     */
    private String parentName;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 图片路径
     */
    private String picPath;
    /**
     * 图片Id
     */
    private Integer picId;
    /**
     * 图片标识
     */
    private Integer mark;
    /**
     * 删除标志
     */
    private String delFlag;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("personalId", getPersonalId())
                .append("menuId", getMenuId())
                .append("userId", getUserId())
                .append("companyId", getDeptId())
                .append("parentId", getParentId())
                .append("parentName", getParentName())
                .append("menuName", getMenuName())
                .append("picPath", getPicPath())
                .append("mark", getMark())
                .toString();
    }
}

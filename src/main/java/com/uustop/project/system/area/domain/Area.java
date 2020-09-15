package com.uustop.project.system.area.domain;

import com.uustop.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uustop.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 所属地区表 sys_area
 *
 * @author xh
 * @Date: 2019-02-27
 */
public class Area extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "id")
    private Integer areaId;
    /**
     * 父id
     */
    @Excel(name = "父id")
    private Integer pid;
    /**
     * 简称
     */
    @Excel(name = "简称")
    private String shortName;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 全称
     */
    @Excel(name = "全称")
    private String mergerName;
    /**
     * 层级 0 1 2 省市区县
     */
    @Excel(name = "id", readConverterExp = "0=省,1=市,2=区/县")
    private Integer level;

    private String tree;

    /**
     * 拼音
     */
    @Excel(name = "拼音")
    private String pinyin;
    /**
     * 长途区号
     */
    @Excel(name = "长途区号")
    private String code;
    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String zipCode;
    /**
     * 首字母
     */
    @Excel(name = "首字母")
    private String first;

    /**
     * 提交人
     */
    @Excel(name = "提交人")
    private String createBy;
    /**
     * 提交时间
     */
    @Excel(name = "提交时间", dateFormat = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH-mm-ss")
    private Date updateTime;

    /**
     * 省份
     */
    private Integer provinceId;
    /**
     * 城市
     */
    private Integer cityId;
    /**
     * 县区
     */
    private Integer countyId;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFirst() {
        return first;
    }



    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Area(Integer level) {
        this.level = level;
    }

    public Area() {
    }

    public Area(Integer areaId, Integer pid, String shortName, String name, String mergerName, Integer level, String pinyin, String code, String zipCode, String first, String delFlag, String createBy, Date createTime, String updateBy, Date updateTime) {
        this.areaId = areaId;
        this.pid = pid;
        this.shortName = shortName;
        this.name = name;
        this.mergerName = mergerName;
        this.level = level;
        this.pinyin = pinyin;
        this.code = code;
        this.zipCode = zipCode;
        this.first = first;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("areaId", getAreaId())
                .append("pid", getPid())
                .append("shortName", getShortName())
                .append("name", getName())
                .append("mergerName", getMergerName())
                .append("level", getLevel())
                .append("pinyin", getPinyin())
                .append("code", getCode())
                .append("zipCode", getZipCode())
                .append("first", getFirst())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

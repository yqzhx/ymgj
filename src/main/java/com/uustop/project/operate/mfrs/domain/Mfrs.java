package com.uustop.project.operate.mfrs.domain;

import com.uustop.framework.aspectj.lang.annotation.Excel;
import com.uustop.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.security.PrivateKey;
import java.util.Date;

/**
 * 合作企业表 dis_mfrs
 *
 * @Auther: xh
 * @Date: 2019-02-15
 */
public class Mfrs extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "id")
    private Integer mfrsId;
    /**
     * 合作企业名称
     */
    @Excel(name = "合作企业", width = 40)
    private String name;
    /**
     * 生产用户姓名
     */
    @Excel(name = "简称")
    private String shortName;

    @Excel(name = "企业抓取名称")
    private String grabbingName;
    /**
     * 合作企业简介
     */
    @Excel(name = "简介")
    private String description;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=存在,1=删除")
    private String delFlag;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Excel(name = "审核通过标识", readConverterExp = " 0通过 1未通过")
    private String isPass;

    /**
     * 提交人
     */
    @Excel(name = "提交人")
    private String createBy;
    /**
     * 提交时间
     */
    @Excel(name = "提交时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 层级名称（河北省-石家庄市-长安区）
     */
    private String areaArr;

    private Date confirmTime;

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

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

    private String linkmanName;

    private String phoneNumber;

    private Integer number;

    /**
     * 提交人
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGrabbingName() {
        return grabbingName;
    }

    public void setGrabbingName(String grabbingName) {
        this.grabbingName = grabbingName;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getAreaArr() {
        return areaArr;
    }

    public void setAreaArr(String areaArr) {
        this.areaArr = areaArr;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
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

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("mfrsId", getMfrsId())
                .append("name", getName())
                .append("shortName", getShortName())
                .append("description", getDescription())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("grabbingName", getGrabbingName())
                .toString();
    }
}

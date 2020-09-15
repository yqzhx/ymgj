package com.uustop.project.operate.picture.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.uustop.framework.config.UUSTOPConfig;
import com.uustop.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 图片表 sys_picture
 *
 * @author xh
 * @Date: 2019-02-26
 */
public class Picture extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 项目图片编号
     */
    private Integer picId;
    /**
     * 图片文件路径
     */
    private String picFilepath;
    /**
     * 图片名字
     */
    private String name;
    /**
     * 图片原名称
     */
    private String oldName;
    /**
     * 类型（0：企业相关）
     */
    private Integer type;
    /**
     * 图片大小
     */
    private String fileSize;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;
    /**
     * 提交人
     */
    private String createBy;
    /**
     * 提交时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer mfrsPictureId;
    /**
     * 合作企业编号
     */
    private Integer mfrsId;
    /**
     * 合作企业临时表id
     */
    private Integer mfrsPrestoreId;
    /**
     * 0合同照片1发票
     */
    private Integer state;

    private String picPath;

    /**
     * 缩略图1
     */
    private String oneLargest;

    /**
     * 缩略图2
     */
    private String secondLargest;


    public String getOneLargest() {
        return UUSTOPConfig.getReloadImgUrl() + oneLargest;
    }

    public void setOneLargest(String oneLargest) {
        this.oneLargest = oneLargest;
    }

    public String getSecondLargest() {
        return UUSTOPConfig.getReloadImgUrl() + secondLargest;
    }

    public void setSecondLargest(String secondLargest) {
        this.secondLargest = secondLargest;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicFilepath(String picFilepath) {
        this.picFilepath = picFilepath;
    }

    public String getPicFilepath() {
        return picFilepath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSize() {
        return fileSize;
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

    public Integer getMfrsPictureId() {
        return mfrsPictureId;
    }

    public void setMfrsPictureId(Integer mfrsPictureId) {
        this.mfrsPictureId = mfrsPictureId;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public Integer getMfrsPrestoreId() {
        return mfrsPrestoreId;
    }

    public void setMfrsPrestoreId(Integer mfrsPrestoreId) {
        this.mfrsPrestoreId = mfrsPrestoreId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }


    public String getPicPath() {
        return UUSTOPConfig.getReloadImgUrl() + picFilepath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("picId", getPicId())
                .append("picFilepath", getPicFilepath())
                .append("name", getName())
                .append("type", getType())
                .append("fileSize", getFileSize())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

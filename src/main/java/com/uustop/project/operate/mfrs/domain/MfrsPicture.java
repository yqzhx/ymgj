package com.uustop.project.operate.mfrs.domain;

import com.uustop.project.operate.picture.domain.Picture;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uustop.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 厂商图片中间表 dis_mfrs_picture
 *
 * @author zx
 * @Date: 2019-05-24
 */
public class MfrsPicture extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer mfrsPictureId;
    /**
     * 合作企业编号
     */
    private Integer mfrsId;
    /**
     * 临时表id
     */
    private Integer mfrsPrestoreId;
    /**
     * 项目图片编号
     */
    private Integer picId;
    /**
     * 0合同照片1发票
     */
    private Integer state;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer type;

    private Picture picture;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public void setMfrsPictureId(Integer mfrsPictureId) {
        this.mfrsPictureId = mfrsPictureId;
    }

    public Integer getMfrsPictureId() {
        return mfrsPictureId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsPrestoreId(Integer mfrsPrestoreId) {
        this.mfrsPrestoreId = mfrsPrestoreId;
    }

    public Integer getMfrsPrestoreId() {
        return mfrsPrestoreId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("mfrsPictureId", getMfrsPictureId())
                .append("mfrsId", getMfrsId())
                .append("mfrsPrestoreId", getMfrsPrestoreId())
                .append("picId", getPicId())
                .append("state", getState())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

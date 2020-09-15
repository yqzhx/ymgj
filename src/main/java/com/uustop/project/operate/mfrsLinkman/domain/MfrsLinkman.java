package com.uustop.project.operate.mfrsLinkman.domain;

import com.uustop.framework.web.domain.BaseEntity;

/**
 * 推广商联系人表 prom_linkman
 *
 * @author zx
 * @date 2019-05-22
 */
public class MfrsLinkman extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer mfrsLinkmanId;
    /**
     * 公司ID
     */
    private Integer mfrsId;
    /**
     * 联系人名称
     */
    private String linkName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 地址. 省-市-县-具体地址
     */
    private String address;
    /**
     * 手机号
     */
    private String mobile;
    /** 座机号 */

    private String telephone;
    /**
     * 绑定邮箱
     */
    private String mail;

    /**
     * 职务
     */
    private String job;

    /**
     * 证件地址 多个文件已"|"隔开
     */
    private Integer imgUrl;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMfrsLinkmanId() {
        return mfrsLinkmanId;
    }

    public void setMfrsLinkmanId(Integer mfrsLinkmanId) {
        this.mfrsLinkmanId = mfrsLinkmanId;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }
}

package com.uustop.project.system.notice.domain;

import com.alibaba.fastjson.JSON;
import com.uustop.framework.web.domain.BaseEntity;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 通知公告表 sys_notice
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-04-19
 */
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Integer noticeId;
    /**
     * 公告类型:{1:注册通知 2:到期提醒 3:故障反馈 4:活动通知 5:会议通知 6:平台公告}
     */
    private Integer noticeType;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 企业类型：{0：推广商  1：合作企业 2：疾控中心}
     */
    private Integer enterpriseType;
    /**
     * 企业id
     */
    private Integer enterpriseId;
    /**
     * 发送类型:{ 1:自动触发 2:手动触发}
     */
    private Integer sendType;
    /**
     * 接收者：{-1 :所有  其他为用户id}
     */
    private Integer sendTo;
    /**
     * 公告状态：{0：未发送 1：已发送}
     */
    private Integer sendStatus;

    /**
     * 企业
     */
    private String enterpriseName;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(Integer enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getSendTo() {
        return sendTo;
    }

    public void setSendTo(Integer sendTo) {
        this.sendTo = sendTo;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}

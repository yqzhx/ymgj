package com.uustop.project.system.noticeRecord.domain;

import com.alibaba.fastjson.JSON;
import com.uustop.project.system.notice.domain.Notice;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uustop.framework.web.domain.BaseEntity;
import org.apache.poi.hssf.record.NoteRecord;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Date;

/**
 * 通知公告表 sys_notice_record
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-04-19
 */
public class NoticeRecord extends BaseEntity implements Encoder.Text<NoticeRecord> {
    private static final long serialVersionUID = 1L;

    /**
     * 公告记录
     */
    private Integer noticeRecordId;
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
     * 接收者id
     */
    private Integer sendTo;
    /**
     * 公告状态：{0：未读 1：已读}
     */
    private Integer readStatus;

    /**
     * 企业
     */
    private String enterpriseName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 账号名
     */
    private String userName;

    /**
     * 电话
     */
    private String phonenumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void setNoticeRecordId(Integer noticeRecordId) {
        this.noticeRecordId = noticeRecordId;
    }

    public Integer getNoticeRecordId() {
        return noticeRecordId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public Integer getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(Integer enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }


    public void setSendTo(Integer sendTo) {
        this.sendTo = sendTo;
    }

    public Integer getSendTo() {
        return sendTo;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeRecordId", getNoticeRecordId())
                .append("noticeId", getNoticeId())
                .append("noticeType", getNoticeType())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeContent", getNoticeContent())
                .append("enterpriseType", getEnterpriseType())
                .append("enterpriseId", getEnterpriseId())
                .append("sendType", getSendType())
                .append("sendTo", getSendTo())
                .append("readStatus", getReadStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("delFlag", getDelFlag())
                .toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(NoticeRecord object) throws EncodeException {
        return JSON.toJSONString(object);
    }
}

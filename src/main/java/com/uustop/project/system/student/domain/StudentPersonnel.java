package com.uustop.project.system.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Table(name = "student_personnel")
public class StudentPersonnel {
    /* 学生ID */
    @Id
    private Integer stuUserId;

    /* 班级ID */
    @Column(name = "stu_dept_id")
    private Integer stuDeptId;

    /* 登录账号 */
    @Column(name = "stu_login_name")
    private String stuLoginName;

    /* 学生姓名 */
    @Column(name = "stu_user_name")
    private String stuUserName;

    /* 用户类型（00系统用户） */
    @Column(name = "stu_user_type")
    private String stuUserType;

    /* 用户邮箱 */
    @Column(name = "stu_email")
    private String stuEmail;

    /* 手机号码 */
    @Column(name = "stu_phonenumber")
    private String stuPhonenumber;

    /* 用户性别（0男 1女 2未知） */
    @Column(name = "stu_sex")
    private String stuSex;

    /* 头像路径 */
    @Column(name = "stu_avatar")
    private String stuAvatar;

    /* 密码 */
    @Column(name = "stu_password")
    private String stuPassword;

    /* 盐加密 */
    @Column(name = "stu_salt")
    private String stuSalt;

    /* 帐号状态（0正常 1停用） */
    @Column(name = "stu_status")
    private String stuStatus;

    /* 删除标志（0代表存在 1代表删除） */
    @Column(name = "stu_del_flag")
    private String stuDelFlag;

    /* 最后登录IP */
    @Column(name = "stu_login_ip")
    private String stuLoginIp;

    /* 最后登录时间 */
    @Column(name = "stu_login_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stuLoginDate;

    /* 创建者 */
    @Column(name = "stu_create_by")
    private String stuCreateBy;

    /* 创建时间 */
    @Column(name = "stu_create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stuCreateTime;

    /* 更新者 */
    @Column(name = "stu_update_by")
    private String stuUpdateBy;

    /* 更行时间 */
    @Column(name = "stu_update_time")
    private Date stuUpdateTime;

    /* 备注 */
    @Column(name = "stu_remark")
    private String stuRemark;

    /* 生日 */
    @Column(name = "stu_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stuBirthday;

    /* qq */
    @Column(name = "stu_QQ")
    private String stuQQ;

    public Integer getStuUserId() {
        return stuUserId;
    }

    public void setStuUserId(Integer stuUserId) {
        this.stuUserId = stuUserId;
    }

    public Integer getStuDeptId() {
        return stuDeptId;
    }

    public void setStuDeptId(Integer stuDeptId) {
        this.stuDeptId = stuDeptId;
    }

    public String getStuLoginName() {
        return stuLoginName;
    }

    public void setStuLoginName(String stuLoginName) {
        this.stuLoginName = stuLoginName;
    }

    public String getStuUserName() {
        return stuUserName;
    }

    public void setStuUserName(String stuUserName) {
        this.stuUserName = stuUserName;
    }

    public String getStuUserType() {
        return stuUserType;
    }

    public void setStuUserType(String stuUserType) {
        this.stuUserType = stuUserType;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getStuPhonenumber() {
        return stuPhonenumber;
    }

    public void setStuPhonenumber(String stuPhonenumber) {
        this.stuPhonenumber = stuPhonenumber;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuAvatar() {
        return stuAvatar;
    }

    public void setStuAvatar(String stuAvatar) {
        this.stuAvatar = stuAvatar;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getStuSalt() {
        return stuSalt;
    }

    public void setStuSalt(String stuSalt) {
        this.stuSalt = stuSalt;
    }
    /**
     * 生成随机盐
     */
    public void randomSalt()
    {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setStuSalt(hex);
    }
    public String getStuStatus() {
        return stuStatus;
    }

    public void setStuStatus(String stuStatus) {
        this.stuStatus = stuStatus;
    }

    public String getStuDelFlag() {
        return stuDelFlag;
    }

    public void setStuDelFlag(String stuDelFlag) {
        this.stuDelFlag = stuDelFlag;
    }

    public String getStuLoginIp() {
        return stuLoginIp;
    }

    public void setStuLoginIp(String stuLoginIp) {
        this.stuLoginIp = stuLoginIp;
    }

    public Date getStuLoginDate() {
        return stuLoginDate;
    }

    public void setStuLoginDate(Date stuLoginDate) {
        this.stuLoginDate = stuLoginDate;
    }

    public String getStuCreateBy() {
        return stuCreateBy;
    }

    public void setStuCreateBy(String stuCreateBy) {
        this.stuCreateBy = stuCreateBy;
    }

    public Date getStuCreateTime() {
        return stuCreateTime;
    }

    public void setStuCreateTime(Date stuCreateTime) {
        this.stuCreateTime = stuCreateTime;
    }

    public String getStuUpdateBy() {
        return stuUpdateBy;
    }

    public void setStuUpdateBy(String stuUpdateBy) {
        this.stuUpdateBy = stuUpdateBy;
    }

    public Date getStuUpdateTime() {
        return stuUpdateTime;
    }

    public void setStuUpdateTime(Date stuUpdateTime) {
        this.stuUpdateTime = stuUpdateTime;
    }

    public String getStuRemark() {
        return stuRemark;
    }

    public void setStuRemark(String stuRemark) {
        this.stuRemark = stuRemark;
    }

    public Date getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(Date stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    public String getStuQQ() {
        return stuQQ;
    }

    public void setStuQQ(String stuQQ) {
        this.stuQQ = stuQQ;
    }

    public StudentPersonnel() {
    }

    public StudentPersonnel(Integer stuUserId, Integer stuDeptId, String stuLoginName, String stuUserName, String stuUserType, String stuEmail, String stuPhonenumber, String stuSex, String stuAvatar, String stuPassword, String stuSalt, String stuStatus, String stuDelFlag, String stuLoginIp, Date stuLoginDate, String stuCreateBy, Date stuCreateTime, String stuUpdateBy, Date stuUpdateTime, String stuRemark, Date stuBirthday, String stuQQ) {
        this.stuUserId = stuUserId;
        this.stuDeptId = stuDeptId;
        this.stuLoginName = stuLoginName;
        this.stuUserName = stuUserName;
        this.stuUserType = stuUserType;
        this.stuEmail = stuEmail;
        this.stuPhonenumber = stuPhonenumber;
        this.stuSex = stuSex;
        this.stuAvatar = stuAvatar;
        this.stuPassword = stuPassword;
        this.stuSalt = stuSalt;
        this.stuStatus = stuStatus;
        this.stuDelFlag = stuDelFlag;
        this.stuLoginIp = stuLoginIp;
        this.stuLoginDate = stuLoginDate;
        this.stuCreateBy = stuCreateBy;
        this.stuCreateTime = stuCreateTime;
        this.stuUpdateBy = stuUpdateBy;
        this.stuUpdateTime = stuUpdateTime;
        this.stuRemark = stuRemark;
        this.stuBirthday = stuBirthday;
        this.stuQQ = stuQQ;
    }

    @Override
    public String toString() {
        return "StudentPersonnel{" +
                "stuUserId=" + stuUserId +
                ", stuDeptId=" + stuDeptId +
                ", stuLoginName='" + stuLoginName + '\'' +
                ", stuUserName='" + stuUserName + '\'' +
                ", stuUserType='" + stuUserType + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuPhonenumber='" + stuPhonenumber + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuAvatar='" + stuAvatar + '\'' +
                ", stuPassword='" + stuPassword + '\'' +
                ", stuSalt='" + stuSalt + '\'' +
                ", stuStatus='" + stuStatus + '\'' +
                ", stuDelFlag='" + stuDelFlag + '\'' +
                ", stuLoginIp='" + stuLoginIp + '\'' +
                ", stuLoginDate=" + stuLoginDate +
                ", stuCreateBy='" + stuCreateBy + '\'' +
                ", stuCreateTime=" + stuCreateTime +
                ", stuUpdateBy='" + stuUpdateBy + '\'' +
                ", stuUpdateTime=" + stuUpdateTime +
                ", stuRemark='" + stuRemark + '\'' +
                ", stuBirthday=" + stuBirthday +
                ", stuQQ='" + stuQQ + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentPersonnel that = (StudentPersonnel) o;
        return Objects.equals(stuUserId, that.stuUserId) &&
                Objects.equals(stuDeptId, that.stuDeptId) &&
                Objects.equals(stuLoginName, that.stuLoginName) &&
                Objects.equals(stuUserName, that.stuUserName) &&
                Objects.equals(stuUserType, that.stuUserType) &&
                Objects.equals(stuEmail, that.stuEmail) &&
                Objects.equals(stuPhonenumber, that.stuPhonenumber) &&
                Objects.equals(stuSex, that.stuSex) &&
                Objects.equals(stuAvatar, that.stuAvatar) &&
                Objects.equals(stuPassword, that.stuPassword) &&
                Objects.equals(stuSalt, that.stuSalt) &&
                Objects.equals(stuStatus, that.stuStatus) &&
                Objects.equals(stuDelFlag, that.stuDelFlag) &&
                Objects.equals(stuLoginIp, that.stuLoginIp) &&
                Objects.equals(stuLoginDate, that.stuLoginDate) &&
                Objects.equals(stuCreateBy, that.stuCreateBy) &&
                Objects.equals(stuCreateTime, that.stuCreateTime) &&
                Objects.equals(stuUpdateBy, that.stuUpdateBy) &&
                Objects.equals(stuUpdateTime, that.stuUpdateTime) &&
                Objects.equals(stuRemark, that.stuRemark) &&
                Objects.equals(stuBirthday, that.stuBirthday) &&
                Objects.equals(stuQQ, that.stuQQ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuUserId, stuDeptId, stuLoginName, stuUserName, stuUserType, stuEmail, stuPhonenumber, stuSex, stuAvatar, stuPassword, stuSalt, stuStatus, stuDelFlag, stuLoginIp, stuLoginDate, stuCreateBy, stuCreateTime, stuUpdateBy, stuUpdateTime, stuRemark, stuBirthday, stuQQ);
    }
}

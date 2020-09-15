package com.uustop.project.system.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;
@Table(name = "student_class")
public class StudentClass{

    /* 班级id */
    @Id
    private Integer stuDeptId;

    /* 父部门id */
    @Column(name = "stu_parent_id")
    private Integer stuParentId;

    /* 祖级列表 */
    @Column(name = "stu_ancestors")
    private String stuAncestors;

    /* 班级名称 */
    @Column(name = "stu_dept_name")
    private String stuDeptName;

    /* 显示顺序 */
    @Column(name = "stu_order_num")
    private Integer stuOrderNum;

    /* 负责人 */
    @Column(name = "stu_leader")
    private String stuLeader;

    /* 联系电话 */
    @Column(name = "stu_phone")
    private String stuPhone;

    /* 邮箱 */
    @Column(name = "stu_email")
    private String stuEmail;

    /* 部门状态（0正常 1停用） */
    @Column(name = "stu_status")
    private String stuStatus;

    /* 删除标志（0代表存在 1代表删除） */
    @Column(name = "stu_del_flag")
    private String stuDelFlag;

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

    /* 更新时间 */
    @Column(name = "stu_update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stuUpdateTime;

    public Integer getStuDeptId() {
        return stuDeptId;
    }

    public void setStuDeptId(Integer stuDeptId) {
        this.stuDeptId = stuDeptId;
    }

    public Integer getStuParentId() {
        return stuParentId;
    }

    public void setStuParentId(Integer stuParentId) {
        this.stuParentId = stuParentId;
    }

    public String getStuAncestors() {
        return stuAncestors;
    }

    public void setStuAncestors(String stuAncestors) {
        this.stuAncestors = stuAncestors;
    }

    public String getStuDeptName() {
        return stuDeptName;
    }

    public void setStuDeptName(String stuDeptName) {
        this.stuDeptName = stuDeptName;
    }

    public Integer getStuOrderNum() {
        return stuOrderNum;
    }

    public void setStuOrderNum(Integer stuOrderNum) {
        this.stuOrderNum = stuOrderNum;
    }

    public String getStuLeader() {
        return stuLeader;
    }

    public void setStuLeader(String stuLeader) {
        this.stuLeader = stuLeader;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
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

    public StudentClass(Integer stuDeptId, Integer stuParentId, String stuAncestors, String stuDeptName, Integer stuOrderNum, String stuLeader, String stuPhone, String stuEmail, String stuStatus, String stuDelFlag, String stuCreateBy, Date stuCreateTime, String stuUpdateBy, Date stuUpdateTime) {
        this.stuDeptId = stuDeptId;
        this.stuParentId = stuParentId;
        this.stuAncestors = stuAncestors;
        this.stuDeptName = stuDeptName;
        this.stuOrderNum = stuOrderNum;
        this.stuLeader = stuLeader;
        this.stuPhone = stuPhone;
        this.stuEmail = stuEmail;
        this.stuStatus = stuStatus;
        this.stuDelFlag = stuDelFlag;
        this.stuCreateBy = stuCreateBy;
        this.stuCreateTime = stuCreateTime;
        this.stuUpdateBy = stuUpdateBy;
        this.stuUpdateTime = stuUpdateTime;
    }

    public StudentClass() {
    }

    @Override
    public String toString() {
        return "StudentPersonnel{" +
                "stuDeptId=" + stuDeptId +
                ", stuParentId=" + stuParentId +
                ", stuAncestors='" + stuAncestors + '\'' +
                ", stuDeptName='" + stuDeptName + '\'' +
                ", stuOrderNum=" + stuOrderNum +
                ", stuLeader='" + stuLeader + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuStatus='" + stuStatus + '\'' +
                ", stuDelFlag='" + stuDelFlag + '\'' +
                ", stuCreateBy='" + stuCreateBy + '\'' +
                ", stuCreateTime=" + stuCreateTime +
                ", stuUpdateBy='" + stuUpdateBy + '\'' +
                ", stuUpdateTime=" + stuUpdateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentClass that = (StudentClass) o;
        return Objects.equals(stuDeptId, that.stuDeptId) &&
                Objects.equals(stuParentId, that.stuParentId) &&
                Objects.equals(stuAncestors, that.stuAncestors) &&
                Objects.equals(stuDeptName, that.stuDeptName) &&
                Objects.equals(stuOrderNum, that.stuOrderNum) &&
                Objects.equals(stuLeader, that.stuLeader) &&
                Objects.equals(stuPhone, that.stuPhone) &&
                Objects.equals(stuEmail, that.stuEmail) &&
                Objects.equals(stuStatus, that.stuStatus) &&
                Objects.equals(stuDelFlag, that.stuDelFlag) &&
                Objects.equals(stuCreateBy, that.stuCreateBy) &&
                Objects.equals(stuCreateTime, that.stuCreateTime) &&
                Objects.equals(stuUpdateBy, that.stuUpdateBy) &&
                Objects.equals(stuUpdateTime, that.stuUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuDeptId, stuParentId, stuAncestors, stuDeptName, stuOrderNum, stuLeader, stuPhone, stuEmail, stuStatus, stuDelFlag, stuCreateBy, stuCreateTime, stuUpdateBy, stuUpdateTime);
    }
}

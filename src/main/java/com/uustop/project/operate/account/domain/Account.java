package com.uustop.project.operate.account.domain;

import com.uustop.framework.aspectj.lang.annotation.Excel;
import com.uustop.project.operate.picture.domain.Picture;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uustop.framework.web.domain.BaseEntity;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Date;

/**
 * 用户表 prom_account
 *
 * @Auther: xh
 * @Date: : 2019-02-25
 */
public class Account extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Excel(name = "id")
    private Integer accountId;
    /**
     * 组织机构ID
     */
    private Integer deptId;
    /**
     * 账号名
     */
    @Excel(name = "账号名")
    private String loginName;
    /**
     * 用户姓名
     */
    @Excel(name = "用户姓名")
    private String userName;
    /**
     * 1:人员（普通） 2:企业（管理员）
     */
    @Excel(name = "账号类型", readConverterExp = "1=普通人员,2=管理员")
    private String userType;
    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "id", readConverterExp = "0=男,1=女,2=未知")
    private String sex;
    /**
     * 头像路径
     */
    private Integer avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐加密
     */
    private String salt;
    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP")
    private String loginIp;
    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间",dateFormat ="yyyy-MM-dd HH-mm-ss")
    private Date loginDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=存在,1=删除")
    private String delFlag;
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



    private String companyName;


    private String roleName;

    //图片
    private Picture picture;

    /**
     * cdc主体id
     */
    private Integer cdcId;


    private String roleArr;

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public String getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(String roleArr) {
        this.roleArr = roleArr;
    }

    public Integer getCdcId() {
        return cdcId;
    }

    public void setCdcId(Integer cdcId) {
        this.cdcId = cdcId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
                .append("accountId", getAccountId())
                .append("deptId", getDeptId())
                .append("loginName", getLoginName())
                .append("userName", getUserName())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("remark", getRemark())
                .append("companyId", getCompanyId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

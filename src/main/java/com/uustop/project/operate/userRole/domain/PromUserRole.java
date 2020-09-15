package com.uustop.project.operate.userRole.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uustop.framework.web.domain.BaseEntity;

/**
 * 用户和角色关联表 prom_user_role
 *
 * @author xh
 * @Date: 2019-03-13
 */
public class PromUserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer accountId;
    /**
     * 角色ID
     */
    private Integer roleId;

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("accountId", getAccountId())
                .append("roleId", getRoleId())
                .toString();
    }
}

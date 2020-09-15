package com.uustop.project.operate.mfrs.mapper;

import java.util.Map;

/**
 * 用户表 数据层
 *
 * @author uustop
 */
public interface MfrsUserRoleMapper {
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param accountId 用户ID
     * @return 结果
     */
    public int deleteUserRoleByAccountId(Long accountId);


    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(Map<String, Object> map);


    public Long selectMfrsUserByMfrsId(String id);
}

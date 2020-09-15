package com.uustop.project.operate.userRole.service;

import com.uustop.project.operate.userRole.domain.PromUserRole;

import java.util.List;

/**
 * 用户和角色关联 服务层
 *
 * @author xh
 * @Date: 2019-03-13
 */
public interface PromUserRoleService {
    /**
     * 查询用户和角色关联信息
     *
     * @param accountId 用户和角色关联ID
     * @return 用户和角色关联信息
     */
    public PromUserRole selectUserRoleById(Integer accountId);

    /**
     * 查询用户和角色关联列表
     *
     * @param userRole 用户和角色关联信息
     * @return 用户和角色关联集合
     */
    public List<PromUserRole> selectUserRoleList(PromUserRole userRole);

    /**
     * 新增用户和角色关联
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int insertUserRole(PromUserRole userRole);

    /**
     * 修改用户和角色关联
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int updateUserRole(PromUserRole userRole);

    /**
     * 删除用户和角色关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRoleByIds(String ids);

}

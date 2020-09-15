package com.uustop.project.operate.userRole.service;

import java.util.List;

import com.uustop.project.operate.userRole.mapper.PromUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uustop.project.operate.userRole.domain.PromUserRole;
import com.uustop.common.support.Convert;

/**
 * 用户和角色关联 服务层实现
 *
 * @author xh
 * @Date: 2019-03-13
 */
@Service
public class UserRoleServiceImpl implements PromUserRoleService {
    @Autowired
    private PromUserRoleMapper userRoleMapper;

    /**
     * 查询用户和角色关联信息
     *
     * @param accountId 用户和角色关联ID
     * @return 用户和角色关联信息
     */
    @Override
    public PromUserRole selectUserRoleById(Integer accountId) {
        return userRoleMapper.selectUserRoleById(accountId);
    }

    /**
     * 查询用户和角色关联列表
     *
     * @param userRole 用户和角色关联信息
     * @return 用户和角色关联集合
     */
    @Override
    public List<PromUserRole> selectUserRoleList(PromUserRole userRole) {
        return userRoleMapper.selectUserRoleList(userRole);
    }

    /**
     * 新增用户和角色关联
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int insertUserRole(PromUserRole userRole) {
        return userRoleMapper.insertUserRole(userRole);
    }

    /**
     * 修改用户和角色关联
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int updateUserRole(PromUserRole userRole) {
        return userRoleMapper.updateUserRole(userRole);
    }

    /**
     * 删除用户和角色关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleByIds(String ids) {
        return userRoleMapper.deleteUserRoleByIds(Convert.toStrArray(ids));
    }

}

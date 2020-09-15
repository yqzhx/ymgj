package com.uustop.project.operate.account.service;


import com.uustop.project.operate.account.domain.Account;
import com.uustop.project.system.user.domain.User;

import java.util.List;

/**
 * 用户 服务层
 *
 * @author tianhui.yu@hqq.vip
 * @date 2019-02-20
 */
public interface IAccountService {
    /**
     * 查询用户信息
     *
     * @param disAccountId 用户ID
     * @return 用户信息
     */
    Account selectAccountById(Integer disAccountId);

    /**
     * 查询用户列表
     *
     * @param account 用户信息
     * @return 用户集合
     */
    List<Account> selectAccountList(Account account);

    /**
     * 新增用户
     *
     * @param account 用户信息
     * @return 结果
     */
    int insertAccount(Account account);

    /**
     * 修改用户
     *
     * @param account 用户信息
     * @return 结果
     */
    int updateAccount(Account account);

    /**
     * 删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAccountByIds(String ids);

    /**
     * 根据账号名查用户
     *
     * @param loginName 账号名
     * @return 结果
     */
    int selectByLoginName(String loginName);

    /**
     * 根据loginName获得userName
     *
     * @return 结果
     */
    List<User> selectUserNameByLoginName();

    /**
     * 企业账号
     *
     * @param account 账号
     * @return 结果
     */
    List<Account> selectNotAdminAccountList(Account account);

    List<Account> selectAdminAccountList(Account account);
}

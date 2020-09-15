package com.uustop.project.operate.account.service;


import com.uustop.common.support.Convert;
import com.uustop.project.operate.account.domain.Account;
import com.uustop.project.operate.account.mapper.AccountMapper;
import com.uustop.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 服务层实现
 *
 * @author tianhui.yu@hqq.vip
 * @date 2019-02-20
 */
@Service("operateAccount")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询用户信息
     *
     * @param disAccountId 用户ID
     * @return 用户信息
     */
    @Override
    public Account selectAccountById(Integer disAccountId) {
        return accountMapper.selectAccountById(disAccountId);
    }

    /**
     * 查询用户列表
     *
     * @param account 用户信息
     * @return 用户集合
     */
    @Override
    public List<Account> selectAccountList(Account account) {
        return accountMapper.selectAccountList(account);
    }


    /**
     * 新增用户
     *
     * @param account 用户信息
     * @return 结果
     */
    @Override
    public int insertAccount(Account account) {
        return accountMapper.insertAccount(account);
    }

    /**
     * 修改用户
     *
     * @param account 用户信息
     * @return 结果
     */
    @Override
    public int updateAccount(Account account) {
        return accountMapper.updateAccount(account);
    }

    /**
     * 删除用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountByIds(String ids) {
        return accountMapper.deleteAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 根据账号名查用户
     *
     * @param loginName 账号名
     * @return 结果
     */
    @Override
    public int selectByLoginName(String loginName) {
        return accountMapper.selectByLoginName(loginName);
    }

    @Override
    public List<User> selectUserNameByLoginName() {
        return accountMapper.selectUserNameByLoginName();
    }

    @Override
    public List<Account> selectNotAdminAccountList(Account account) {
        return accountMapper.selectNotAdminAccountList(account);
    }

    @Override
    public List<Account> selectAdminAccountList(Account account) {
        return accountMapper.selectAdminAccountList(account);
    }


}

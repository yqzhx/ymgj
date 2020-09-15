package com.uustop.project.operate.mfrs.service;

import com.uustop.project.operate.mfrs.domain.MfrsAccount;
import com.uustop.project.operate.mfrs.mapper.MfrsAccountMapper;
import com.uustop.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author uustop
 */
@Service
public class MfrsAccountServiceImpl implements MfrsAccountService {

    @Autowired
    private MfrsAccountMapper userMapper;

    /**
     * 根据条件分页查询用户对象
     *
     * @param mfrsAccount 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<MfrsAccount> selectUserList(MfrsAccount mfrsAccount) {
        return userMapper.selectUserList(mfrsAccount);
    }

    /**
     * 通过账号名查询用户
     *
     * @param userName 账号名
     * @return 用户对象信息
     */
    @Override
    public MfrsAccount selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

}

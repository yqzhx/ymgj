package com.uustop.project.operate.mfrs.service;

import com.uustop.project.operate.mfrs.domain.MfrsAccount;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author uustop
 */
public interface MfrsAccountService {

    /**
     * 根据条件分页查询用户对象
     *
     * @param mfrsAccount 用户信息
     * @return 用户信息集合信息
     */
    List<MfrsAccount> selectUserList(MfrsAccount mfrsAccount);


    /**
     * 通过账号名查询用户
     *
     * @param userName 账号名
     * @return 用户对象信息
     */
    MfrsAccount selectUserByLoginName(String userName);

}

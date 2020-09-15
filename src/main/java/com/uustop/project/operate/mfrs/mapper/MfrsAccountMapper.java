package com.uustop.project.operate.mfrs.mapper;


import com.uustop.project.operate.mfrs.domain.MfrsAccount;
import com.uustop.project.system.user.domain.User;

import java.util.List;


/**
 * 合作企业用户 数据层
 */
public interface MfrsAccountMapper {

    /**
     * 通过账号名查询用户
     *
     * @param userName 账号名
     * @return 用户对象信息
     */
     MfrsAccount selectUserByLoginName(String userName);

    /**
     * 修改合作企业用户
     *
     * @param mfrsAccount 合作企业信息
     * @return 结果
     */
    int updateMfrsAccount(MfrsAccount mfrsAccount);


    /**
     * 根据条件分页查询用户对象
     *
     * @param mfrsAccount 用户信息
     * @return 用户信息集合信息
     */
    List<MfrsAccount> selectUserList(MfrsAccount mfrsAccount);

}
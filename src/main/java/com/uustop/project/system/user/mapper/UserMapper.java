package com.uustop.project.system.user.mapper;

import com.uustop.project.system.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author uustop
 */
public interface UserMapper {

    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user);

    /**
     * 通过账号名查询用户
     *
     * @param userName 账号名
     * @return 用户对象信息
     */
    public User selectUserByLoginName(String userName);

    /**
     * 通过手机号查询用户
     *
     * @param phoneNumber 手机号
     * @return 用户对象信息
     */
    public User selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    public User selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);


    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @return 结果
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号是否唯一
     *
     * @param phonenumber 手机号
     * @return 结果
     */
    public User checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public User checkEmailUnique(String email);

    /**
     * 根据loginName获得userName
     *
     * @return 结果
     */
    List<User> selectUserNameByLoginName();

    /**
     * 根据loginName获得userName
     *
     * @param type 用户表
     * @return 结果
     */
    List<User> selectUserNameByLoginNameByType(@Param("type") Integer type);

    public User selectMfrsAccountByLoginName(String userName);

    /**
     * 根据门诊 查询梯户数量
     *
     * @param clinicId 门诊id
     * @return 结果
     */
    int selectCountByClinicId(Integer clinicId);
}

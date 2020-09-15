package com.uustop.project.system.student.service;

import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.user.domain.User;

import java.util.List;

public interface StudentPersonnelService {

    /**
     * 根据条件分页查询用户对象
     *
     * @param studentPersonnel 用户信息
     * @return 用户信息集合信息
     */
    public List<StudentPersonnel> selectStudentList(StudentPersonnel studentPersonnel);

    /**
     * 通过账号名查询用户
     *
     * @param userName 账号名
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByLoginName(String userName);

    /**
     * 通过手机号查询用户
     *
     * @param phoneNumber 手机号
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByEmail(String email);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentById(Long userId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteStudentById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteStudentByIds(String ids) throws Exception;

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertStudent(StudentPersonnel user);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateStudent(StudentPersonnel user);

    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateStudentInfo(StudentPersonnel user);

    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetStudentPwd(StudentPersonnel user);

    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(StudentPersonnel user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(StudentPersonnel user);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    public String selectStudentRoleGroup(Long userId);


    /**
     * 根据loginName获得userName
     *
     * @return 结果
     */
    public List<StudentPersonnel> selectStudentUserNameByLoginName();

    public int statusStudentById(Long accountId);

    public StudentPersonnel selectMfrsAccountByLoginName(String userName);

    /**
     * 根据门诊 查询梯户数量
     *
     * @param clinicId 门诊id
     * @return 结果
     */
    int selectCountByClinicId(Integer clinicId);


    /*
     * 根据学生ID删除
     *
     * @param personnelId 需要删除的学生的id
     * *//*
    void deletePersonnelByPersonnelId(String personnelId);

    *//*
     * 添加学生信息
     *
     * @param stuentPersonnel 需要添加的学生对象
     * *//*
    void insertPersonnel(StudentPersonnel studentPersonnel);

    *//*
     * 根据学生ID修改
     *
     * @param studentPersonnel
     * *//*
    void updatePersonnelByPersonnelId(StudentPersonnel studentPersonnel);

    *//*
     * 查询全部学生
     *
     * @return 返回全部学生的对象集合
     * *//*
    List<StudentPersonnel> selectAllPersonnel();

    *//*
     * 根据学生ID查询对象信息
     *
     * @param studentId 需要查询的学生ID
     * @return 返回查询到的学生对象
     * *//*
    StudentPersonnel selectByPersonnelId(String studentPersonnelId);

    *//*
     * 根据用户信息模糊查询用户
     *
     * @param column 查询哪一列数据
     * @param condition 具体的查询条件
     * @return 返回学生对象集合
     * *//*
    List<StudentPersonnel> selectPersonnelByCondition(String column,String condition);

    *//*
     * 根据班级ID查询学生信息
     *
     * @param stuDeptId
     * @return 返回学生集合
     * *//*
    List<StudentPersonnel> selectPersonnelByDeptId(String stuDeptId);

    *//*
     * 根据班级id查询学生信息
     *
     * @param List<Integer> stuDeptId 班级id
     * @return 返回学生对象
     * *//*
    List<StudentPersonnel> selectPersonnelByStuDeptId(List<StudentClass> stuDeptId);*/
}

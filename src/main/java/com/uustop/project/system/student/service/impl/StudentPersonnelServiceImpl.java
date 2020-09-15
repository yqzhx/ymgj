package com.uustop.project.system.student.service.impl;

import com.uustop.common.constant.UserConstants;
import com.uustop.common.support.Convert;
import com.uustop.common.utils.RedisUtils;
import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.security.ShiroUtils;
import com.uustop.framework.shiro.service.PasswordService;
import com.uustop.project.system.role.domain.Role;
import com.uustop.project.system.role.mapper.RoleMapper;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.mapper.StudentPersonnelMapper;
import com.uustop.project.system.student.service.StudentPersonnelService;
import com.uustop.project.system.user.domain.User;
import com.uustop.project.system.user.domain.UserRole;
import com.uustop.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentPersonnelServiceImpl implements StudentPersonnelService {

    @Autowired
    private StudentPersonnelMapper studentPersonnelMapper;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleMapper roleMapper;
    /**
     * 根据条件分页查询用户对象
     *
     * @param studentPersonnel 用户信息
     * @return 用户信息集合信息
     */
    public List<StudentPersonnel> selectStudentList(StudentPersonnel studentPersonnel) {
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectStudentPersonnelList(studentPersonnel);
        return studentPersonnels;
    }

    /**
     * 通过账号名查询用户
     *
     * @param stuUserName 账号名
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByLoginName(String stuUserName) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuLoginName", stuUserName);
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectOneByExample(example);
        return studentPersonnel;
    }

    /**
     * 通过手机号查询用户
     *
     * @param stuPhoneNumber 手机号
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByPhoneNumber(String stuPhoneNumber) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuPhoneNumber", stuPhoneNumber);
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectOneByExample(example);
        return studentPersonnel;
    }

    /**
     * 通过邮箱查询用户
     *
     * @param stuEmail 邮箱
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentByEmail(String stuEmail) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuEmail", stuEmail);
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectOneByExample(example);
        return studentPersonnel;
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public StudentPersonnel selectStudentById(Long userId) {
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectByPrimaryKey(userId);
        return studentPersonnel;
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteStudentById(Long userId) {
        return studentPersonnelMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteStudentByIds(String ids) throws Exception {

        Long[] accountIds = Convert.toLongArray(ids);
        for (Long accountId : accountIds) {
            StudentPersonnel studentPersonnel = studentPersonnelMapper.selectByPrimaryKey(accountId);
            studentPersonnel.setStuDelFlag("1");
            studentPersonnel.setStuUpdateBy(ShiroUtils.getLoginName());
            studentPersonnelMapper.deleteByPrimaryKey(accountId);
        }
        return 1;
    }

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertStudent(StudentPersonnel user) {
        user.randomSalt();
        user.setStuPassword(passwordService.encryptPassword(user.getStuLoginName(), user.getStuPassword(), user.getStuSalt()));
        user.setStuCreateBy(ShiroUtils.getLoginName());
        // 新增用户信息
        int rows = studentPersonnelMapper.insertSelective(user);
        return rows;
    }

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateStudent(StudentPersonnel user) {
        Long userId = user.getStuUserId();
        user.setStuUpdateBy(ShiroUtils.getLoginName());

        List<String> lrange = redisUtils.lrange(user.getStuUserId() + user.getStuLoginName() + user.getStuPhonenumber(), 0, -1);
        for (String sessionId : lrange) {
            redisUtils.del(sessionId);
        }
        redisUtils.del(user.getStuUserId() + user.getStuLoginName() + user.getStuPhonenumber());

        return studentPersonnelMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateStudentInfo(StudentPersonnel user) {
        return studentPersonnelMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetStudentPwd(StudentPersonnel user) {
        user.randomSalt();
        user.setStuPassword(passwordService.encryptPassword(user.getStuLoginName(), user.getStuPassword(), user.getStuSalt()));
        return updateStudentInfo(user);
    }

    /**
     * 校验用户姓名是否唯一
     *
     * @param loginName 登录帐号
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuLoginName", loginName);
        int count = studentPersonnelMapper.selectCountByExample(example);

        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验手机号是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(StudentPersonnel user) {
        Long userId = StringUtils.isNull(user.getStuUserId()) ? -1L : user.getStuUserId();
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuPhonenumber", user.getStuPhonenumber());
        StudentPersonnel info = studentPersonnelMapper.selectOneByExample(example);
        if (StringUtils.isNotNull(info) && info.getStuUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(StudentPersonnel user) {
        Long userId = StringUtils.isNull(user.getStuUserId()) ? -1L : user.getStuUserId();
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuEmail", user.getStuEmail());
        StudentPersonnel info = studentPersonnelMapper.selectOneByExample(example);
        if (StringUtils.isNotNull(info) && info.getStuUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    public String selectStudentRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }


    /**
     * 根据loginName获得userName
     *
     * @return 结果
     */
    public List<StudentPersonnel> selectStudentUserNameByLoginName() {
        return studentPersonnelMapper.selectUserNameByLoginName();
    }

    public int statusStudentById(Long accountId) {
        StudentPersonnel user = selectStudentById(accountId);
        if (user.getStuStatus().equals("1")) {
            user.setStuStatus("0");
        } else {
            user.setStuStatus("1");
        }
        user.setStuUpdateBy(ShiroUtils.getLoginName());
        return studentPersonnelMapper.updateByPrimaryKeySelective(user);
    }

    public StudentPersonnel selectMfrsAccountByLoginName(String userName) {
        return studentPersonnelMapper.selectMfrsAccountByLoginName(userName);
    }

    /**
     * 根据门诊 查询梯户数量
     *
     * @param clinicId 门诊id
     * @return 结果
     */
    public int selectCountByClinicId(Integer clinicId) {
        return studentPersonnelMapper.selectCountByClinicId(clinicId);
    }



    /*
     * 根据学生ID删除
     *
     * @param personnelId 需要删除的学生的id
     * *//*
    @Override
    public void deletePersonnelByPersonnelId(String personnelId) {
        studentPersonnelMapper.deleteByPrimaryKey(personnelId);
    }
    *//*
     * 添加学生信息
     *
     * @param stuentPersonnel 需要添加的学生对象
     * *//*
    @Override
    public void insertPersonnel(StudentPersonnel studentPersonnel) {
        studentPersonnelMapper.insertSelective(studentPersonnel);
    }
    *//*
     * 根据学生ID修改
     *
     * @param studentPersonnel 需要修改信息的学生
     * *//*
    @Override
    public void updatePersonnelByPersonnelId(StudentPersonnel studentPersonnel) {
        studentPersonnelMapper.updateByPrimaryKeySelective(studentPersonnel);
    }
    *//*
     * 查询全部学生
     *
     * @return 返回全部学生的对象集合
     * *//*
    @Override
    public List<StudentPersonnel> selectAllPersonnel() {
        List<StudentPersonnel> studentPersonnels = null;
        studentPersonnels = studentPersonnelMapper.selectAll();
        return studentPersonnels;
    }
    *//*
     * 根据学生ID查询对象信息
     *
     * @param studentId 需要查询的学生ID
     * @return 返回查询到的学生对象
     * *//*
    @Override
    public StudentPersonnel selectByPersonnelId(String studentPersonnelId) {
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectByPrimaryKey(studentPersonnelId);
        return studentPersonnel;
    }
    *//*
     * 根据用户信息模糊查询用户
     *
     * @param column 查询哪一列数据
     * @param condition 具体的查询条件
     * @return 返回学生对象集合
     * *//*
    @Override
    public List<StudentPersonnel> selectPersonnelByCondition(String column, String condition) {
        //设置列查询条件
        //设置模糊查询条件
        condition = "%" + condition + "%";
        //设置查询条件
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andLike(column, condition);
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectByExample(example);
        return studentPersonnels;
    }
    *//*
     * 根据班级ID查询学生信息
     *
     * @param stuDeptId
     * @return 返回学生集合
     * *//*
    @Override
    public List<StudentPersonnel> selectPersonnelByDeptId(String stuDeptId) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuDeptId", stuDeptId);
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectByExample(example);
        return studentPersonnels;
    }
    *//*
     * 根据班级id查询学生信息
     *
     * @param List<Integer> stuDeptId 班级id
     * @return 返回学生对象
     * *//*
    @Override
    public List<StudentPersonnel> selectPersonnelByStuDeptId(List<StudentClass> stuDeptIds) {
        List<StudentPersonnel> list = null;
        stuDeptIds.forEach((cId)->{
            Example example = new Example(StudentPersonnel.class);
            example.createCriteria().andEqualTo("stuDeptId",cId.getStuDeptId());
            List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectByExample(example);
            list.addAll(studentPersonnels);
        });

        return list;
    }*/
}

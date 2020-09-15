package com.uustop.project.system.student.service;

import com.uustop.project.system.dept.domain.Dept;
import com.uustop.project.system.role.domain.Role;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;

import java.util.List;
import java.util.Map;

public interface StudentClassService {
    /**
     * 查询组织机构管理数据
     *
     * @param studentClass 组织机构信息
     * @return 组织机构信息集合
     */
    public List<StudentClass> selectStudentClassList(StudentClass studentClass);

    /**
     * 查询组织机构管理树
     *
     * @return 所有组织机构信息
     */
    public List<Map<String, Object>> selectStudentClassTree();

    /**
     * 根据角色ID查询菜单
     *
     * @param StudentPersonnel 角色对象
     * @return 菜单列表
     */
    public List<Map<String, Object>> roleStudentClassTreeData(StudentPersonnel StudentPersonnel);

    /**
     * 查询组织机构人数
     *
     * @param parentId 父组织机构ID
     * @return 结果
     */
    public int selectSchoolClassCount(Long parentId);

    /**
     * 查询组织机构是否存在用户
     *
     * @param deptId 组织机构ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkStudentClassExistUser(Long deptId);

    /**
     * 删除组织机构管理信息
     *
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int deleteStudentClassById(Long deptId);

    /**
     * 新增保存组织机构信息
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    public int insertDept(StudentClass studentClass);

    /**
     * 修改保存组织机构信息
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    public int updateStudentClass(StudentClass studentClass);

    /**
     * 根据组织机构ID查询信息
     *
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    public StudentClass selectStudentClassById(Long deptId);

    /**
     * 校验所属机构是否唯一
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    public String checkSchoolClassNameUnique(StudentClass studentClass);
}

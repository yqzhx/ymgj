package com.uustop.project.system.student.mapper;

import com.uustop.project.system.dept.domain.Dept;
import com.uustop.project.system.student.domain.StudentClass;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentClassMapper extends Mapper<StudentClass> {
    /**
     * 根据学生ID查询组织机构
     *
     * @param stuUserId 角色ID
     * @return 组织机构列表
     */
    List<String> selectRoleStuDeptTree(Long stuUserId);


    /**
     * 查询组织机构是否存在用户
     *
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int checkStudentClassExistUser(Long deptId);

    /**
     * 查询组织机构管理数据
     *
     * @param dept 组织机构信息
     * @return 组织机构信息集合
     */
    public List<StudentClass> selectDeptList(StudentClass dept);
}

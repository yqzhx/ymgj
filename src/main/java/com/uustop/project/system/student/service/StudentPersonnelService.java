package com.uustop.project.system.student.service;

import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;

import java.util.List;

public interface StudentPersonnelService {
    /*
     * 根据学生ID删除
     *
     * @param personnelId 需要删除的学生的id
     * */
    void deletePersonnelByPersonnelId(String personnelId);

    /*
     * 添加学生信息
     *
     * @param stuentPersonnel 需要添加的学生对象
     * */
    void insertPersonnel(StudentPersonnel studentPersonnel);

    /*
     * 根据学生ID修改
     *
     * @param studentPersonnel
     * */
    void updatePersonnelByPersonnelId(StudentPersonnel studentPersonnel);

    /*
     * 查询全部学生
     *
     * @return 返回全部学生的对象集合
     * */
    List<StudentPersonnel> selectAllPersonnel();

    /*
     * 根据学生ID查询对象信息
     *
     * @param studentId 需要查询的学生ID
     * @return 返回查询到的学生对象
     * */
    StudentPersonnel selectByPersonnelId(String studentPersonnelId);

    /*
     * 根据用户信息模糊查询用户
     *
     * @param column 查询哪一列数据
     * @param condition 具体的查询条件
     * @return 返回学生对象集合
     * */
    List<StudentPersonnel> selectPersonnelByCondition(String column,String condition);

    /*
     * 根据班级ID查询学生信息
     *
     * @param stuDeptId
     * @return 返回学生集合
     * */
    List<StudentPersonnel> selectPersonnelByDeptId(String stuDeptId);

    /*
     * 根据班级id查询学生信息
     *
     * @param List<Integer> stuDeptId 班级id
     * @return 返回学生对象
     * */
    List<StudentPersonnel> selectPersonnelByStuDeptId(List<StudentClass> stuDeptId);
}

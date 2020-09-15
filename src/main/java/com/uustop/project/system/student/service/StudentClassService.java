package com.uustop.project.system.student.service;

import com.uustop.project.system.student.domain.StudentClass;

import java.util.List;

public interface StudentClassService {
    /*
     * 添加学校、年级、班级信息
     *
     * @param studentClass 要添加的标签信息
     * */
    void insertStudentClass(StudentClass studentClass);

    /*
     * 根据标签ID删除标签信息
     *
     * @param studentClassId 标签ID
     * */
    void deleteClassByClassId(Integer studentClassId);

    /*
     * 根据标签ID修改标签信息
     *
     * @param studentClassId 标签ID
     * */
    void updateClassByClassId(StudentClass StudentClass);

    /*
     * 根据标签ID查询
     *
     * @param studentClassId 标签ID
     * @return 返回一个标签对象
     * */
    StudentClass selectClassByClassId(Integer studentClassId);

    /*
     * 根据机构进行模糊查询
     *
     * @param condition 接受到的查询条件
     * @return 返回标签对象集合
     * */
    List<StudentClass> selectStudentClassByCondition(String condition);

    /*
     * 根据stuDeptId查询该标签下的所有班级Id
     *
     * @param stuDeptId 接收到的标签
     * @return 返回一个Integer的stuDeptId的集合 表示所有的班级
     * */
    List<StudentClass> selectClassIdByStuDeptId(Integer stuDeptId);
}

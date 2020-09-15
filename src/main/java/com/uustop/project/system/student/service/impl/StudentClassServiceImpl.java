package com.uustop.project.system.student.service.impl;

import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.mapper.StudentClassMapper;
import com.uustop.project.system.student.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class StudentClassServiceImpl implements StudentClassService {
    @Autowired
    private StudentClassMapper studentClassMapper;
    /*
     * 添加学校、年级、班级信息
     *
     * @param studentClass 要添加的标签信息
     * */
    @Override
    public void insertStudentClass(StudentClass studentClass) {
        studentClassMapper.insertSelective(studentClass);
    }
    /*
     * 根据标签ID删除标签信息
     *
     * @param studentClassId 标签ID
     * */
    @Override
    public void deleteClassByClassId(Integer studentClassId) {
        studentClassMapper.deleteByPrimaryKey(studentClassId);
    }
    /*
     * 根据标签ID修改标签信息
     *
     * @param studentClassId 标签ID
     * */
    @Override
    public void updateClassByClassId(StudentClass studentClass) {
        studentClassMapper.updateByPrimaryKeySelective(studentClass);
    }
    /*
     * 根据标签ID查询
     *
     * @param studentClassId 标签ID
     * @return 返回一个标签对象
     * */
    @Override
    public StudentClass selectClassByClassId(Integer studentClassId) {
        StudentClass studentClass = studentClassMapper.selectByPrimaryKey(studentClassId);
        return studentClass;
    }
    /*
     * 根据机构进行模糊查询
     *
     * @param condition 接受到的查询条件
     * @return 返回标签对象集合
     * */
    @Override
    public List<StudentClass> selectStudentClassByCondition(String condition) {
        condition = "%" + condition + "%";
        Example example = new Example(StudentClass.class);
        example.createCriteria().andLike("stuDeptName", condition);
        List<StudentClass> studentClasses = studentClassMapper.selectByExample(example);
        return studentClasses;
    }
    /*
     * 根据stuDeptId查询该标签下的所有班级Id
     *
     * @param stuDeptId 接收到的标签
     * @return 返回一个Integer的stuDeptId的集合 表示所有的班级的id
     * */
    @Override
    public List<StudentClass> selectClassIdByStuDeptId(Integer stuDeptId) {
        List<StudentClass> list = null;
        //首先确定该标签的等级
        StudentClass studentClass = studentClassMapper.selectByPrimaryKey(stuDeptId);
        String[] split = studentClass.getStuAncestors().split(",");
        //如果为第三等，则直接返回
        if (split.length == 3) {
            list.add(studentClassMapper.selectByPrimaryKey(stuDeptId));
            return list;
        }
        //如果为第二等，则根据该标签id查询出班级id并返回
        if (split.length == 2) {
            Example example = new Example(StudentClass.class);
            example.createCriteria().andEqualTo("stuParentId", stuDeptId);
            List<StudentClass> studentClasses = studentClassMapper.selectByExample(example);
            //遍历获得班级id
            studentClasses.forEach((stu)->{
                list.add(stu);
            });
            return list;
        }
        //如果为第一等
        if (split.length == 1) {
            //先查询二级标签
            Example example = new Example(StudentClass.class);
            example.createCriteria().andEqualTo("stuParentId", stuDeptId);
            List<StudentClass> studentClasses = studentClassMapper.selectByExample(example);
            //根据二级标签查询班级id,
            studentClasses.forEach((stu)->{
                Example example1 = new Example(StudentClass.class);
                example1.createCriteria().andEqualTo("stuParentId", stu.getStuDeptId());
                List<StudentClass> classes = studentClassMapper.selectByExample(example1);
                //遍历添加班级id到list集合
                classes.forEach((c)->{
                    list.add(c);
                });
            });
        }
        return list;
    }
}

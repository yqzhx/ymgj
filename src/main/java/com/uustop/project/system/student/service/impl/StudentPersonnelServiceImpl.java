package com.uustop.project.system.student.service.impl;

import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.mapper.StudentPersonnelMapper;
import com.uustop.project.system.student.service.StudentPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class StudentPersonnelServiceImpl implements StudentPersonnelService {
    @Autowired
    private StudentPersonnelMapper studentPersonnelMapper;
    /*
     * 根据学生ID删除
     *
     * @param personnelId 需要删除的学生的id
     * */
    @Override
    public void deletePersonnelByPersonnelId(String personnelId) {
        studentPersonnelMapper.deleteByPrimaryKey(personnelId);
    }
    /*
     * 添加学生信息
     *
     * @param stuentPersonnel 需要添加的学生对象
     * */
    @Override
    public void insertPersonnel(StudentPersonnel studentPersonnel) {
        studentPersonnelMapper.insertSelective(studentPersonnel);
    }
    /*
     * 根据学生ID修改
     *
     * @param studentPersonnel 需要修改信息的学生
     * */
    @Override
    public void updatePersonnelByPersonnelId(StudentPersonnel studentPersonnel) {
        studentPersonnelMapper.updateByPrimaryKeySelective(studentPersonnel);
    }
    /*
     * 查询全部学生
     *
     * @return 返回全部学生的对象集合
     * */
    @Override
    public List<StudentPersonnel> selectAllPersonnel() {
        List<StudentPersonnel> studentPersonnels = null;
        studentPersonnels = studentPersonnelMapper.selectAll();
        return studentPersonnels;
    }
    /*
     * 根据学生ID查询对象信息
     *
     * @param studentId 需要查询的学生ID
     * @return 返回查询到的学生对象
     * */
    @Override
    public StudentPersonnel selectByPersonnelId(String studentPersonnelId) {
        StudentPersonnel studentPersonnel = studentPersonnelMapper.selectByPrimaryKey(studentPersonnelId);
        return studentPersonnel;
    }
    /*
     * 根据用户信息模糊查询用户
     *
     * @param column 查询哪一列数据
     * @param condition 具体的查询条件
     * @return 返回学生对象集合
     * */
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
    /*
     * 根据班级ID查询学生信息
     *
     * @param stuDeptId
     * @return 返回学生集合
     * */
    @Override
    public List<StudentPersonnel> selectPersonnelByDeptId(String stuDeptId) {
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo("stuDeptId", stuDeptId);
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectByExample(example);
        return studentPersonnels;
    }
    /*
     * 根据班级id查询学生信息
     *
     * @param List<Integer> stuDeptId 班级id
     * @return 返回学生对象
     * */
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
    }
}

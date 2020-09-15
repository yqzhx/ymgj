package com.uustop.project.system.student.service.impl;

import com.uustop.common.constant.UserConstants;
import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.security.ShiroUtils;
import com.uustop.framework.aspectj.lang.annotation.DataScope;
import com.uustop.project.system.dept.domain.Dept;
import com.uustop.project.system.role.domain.Role;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.mapper.StudentClassMapper;
import com.uustop.project.system.student.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    @Autowired
    private StudentClassMapper studentClassMapper;

    /**
     * 查询组织机构管理数据
     *
     * @param studentClass 组织机构信息
     * @return 组织机构信息集合
     */
    @Override
    public List<StudentClass> selectStudentClassList(StudentClass studentClass) {
        Example example = new Example(studentClass.getClass());
        example.createCriteria().andEqualTo("stuDelFlag", "0");
        if (studentClass.getStuParentId() != null && studentClass.getStuParentId() != 0) {
            example.createCriteria().andEqualTo("stuParentId", studentClass.getStuParentId());
        }
        if (studentClass.getStuDeptName() != null && !studentClass.getStuDeptName().isEmpty() ) {
            example.createCriteria().andEqualTo("stuDeptName", studentClass.getStuDeptName());
        }
        if (studentClass.getStuStatus() != null && !studentClass.getStuStatus().isEmpty()) {
            example.createCriteria().andEqualTo("", studentClass.getStuStatus());
        }
        example.setOrderByClause("stu_parent_id asc,stu_order_num asc,stu_update_time desc");
        List<StudentClass> studentClasses = studentClassMapper.selectByExample(example);
        return studentClasses;
    }

    /**
     * 查询组织机构管理树
     *
     * @return 所有组织机构信息
     */
    @Override
    public List<Map<String, Object>> selectStudentClassTree() {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<StudentClass> deptList = selectStudentClassList(new StudentClass());
        trees = getTrees(deptList, false, null);
        return trees;
    }

    /**
     * 根据角色ID查询菜单
     *
     * @param StudentPersonnel 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Map<String, Object>> roleStudentClassTreeData(StudentPersonnel StudentPersonnel) {
        Long stuUserId = StudentPersonnel.getStuUserId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<StudentClass> stuDeptList = selectStudentClassList(new StudentClass());
        if (StringUtils.isNotNull(stuUserId))
        {

            List<String> roleDeptList = studentClassMapper.selectRoleStuDeptTree(stuUserId);
            trees = getTrees(stuDeptList, true, roleDeptList);
        }
        else
        {
            trees = getTrees(stuDeptList, false, null);
        }
        return trees;
    }

    /**
     * 查询组织机构人数
     *
     * @param parentId 父组织机构ID
     * @return 结果
     */
    @Override
    public int selectSchoolClassCount(Long parentId) {
        Example example = new Example(StudentClass.class);
        example.createCriteria().andEqualTo("stuDelFlag", "0");
        if (parentId != null && parentId != 0) {
            example.createCriteria().andEqualTo("stuParentId", parentId);
        }
        int count = studentClassMapper.selectCountByExample(example);
        return count;
    }


    /**
     * 查询组织机构是否存在用户
     *
     * @param deptId 组织机构ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkStudentClassExistUser(Long deptId) {
        int result = studentClassMapper.checkStudentClassExistUser(deptId);
        return result > 0 ? true : false;
    }


    /**
     * 删除组织机构管理信息
     *
     * @param deptId 组织机构ID
     * @return 结果
     */
    @Override
    public int deleteStudentClassById(Long deptId) {
        return studentClassMapper.deleteByPrimaryKey(deptId);
    }


    /**
     * 新增保存组织机构信息
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    @Override
    public int insertStudentClass(StudentClass studentClass) {
        Example example = new Example(StudentClass.class);
        example.createCriteria().andEqualTo("stuParentId", studentClass.getStuParentId());
        StudentClass info = (StudentClass) studentClassMapper.selectByExample(example);
        studentClass.setStuCreateBy(ShiroUtils.getLoginName());
        studentClass.setStuAncestors(info.getStuAncestors() + "," + studentClass.getStuParentId());
        return studentClassMapper.insertSelective(studentClass);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 组织机构ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId, String ancestors)
    {
        StudentClass studentClass = new StudentClass();
        studentClass.setStuParentId(deptId);
        List<StudentClass> childrens = studentClassMapper.selectDeptList(studentClass);
        if (childrens.size() > 0)
        {
            for (StudentClass children : childrens)
            {
                children.setStuAncestors(ancestors + "," + studentClass.getStuParentId());
                studentClassMapper.updateByPrimaryKeySelective(children);
            }

        }
    }
    /**
     * 修改保存组织机构信息
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    @Override
    public int updateStudentClass(StudentClass studentClass) {
        Example example = new Example(StudentClass.class);
        example.createCriteria().andEqualTo("stuParentId", studentClass.getStuParentId());
        StudentClass info = studentClassMapper.selectOneByExample(example);
        if (StringUtils.isNotNull(info))
        {
            String ancestors = info.getStuAncestors() + "," + studentClass.getStuParentId();
            studentClass.setStuAncestors(ancestors);
            updateDeptChildren(studentClass.getStuDeptId(), ancestors);
        }
        studentClass.setStuUpdateBy(ShiroUtils.getLoginName());
        return studentClassMapper.updateByPrimaryKeySelective(studentClass);
    }


    /**
     * 根据组织机构ID查询信息
     *
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    @Override
    public StudentClass selectStudentClassById(Long deptId) {
        StudentClass studentClass = studentClassMapper.selectByPrimaryKey(deptId);
        return studentClass;
    }


    /**
     * 校验所属机构是否唯一
     *
     * @param studentClass 组织机构信息
     * @return 结果
     */
    @Override
    public String checkSchoolClassNameUnique(StudentClass studentClass) {
        Long deptId = StringUtils.isNull(studentClass.getStuDeptId()) ? -1L : studentClass.getStuDeptId();
        Example example = new Example(StudentClass.class);
        example.createCriteria().andEqualTo("stuDeptName", studentClass.getStuDeptName())
                .andEqualTo("stuParentId", studentClass.getStuParentId())
                .andEqualTo("stuDelFlag", studentClass.getStuDelFlag());
        StudentClass info = studentClassMapper.selectOneByExample(example);
        if (StringUtils.isNotNull(info) && info.getStuDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 对象转组织机构树
     *
     * @param stuDeptList 组织机构列表
     * @param isCheck 是否需要选中
     * @param rolestuDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<StudentClass> stuDeptList, boolean isCheck, List<String> rolestuDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (StudentClass studentClass : stuDeptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(studentClass.getStuStatus()))
            {
                Map<String, Object> stuDeptMap = new HashMap<String, Object>();
                stuDeptMap.put("id", studentClass.getStuDeptId());
                stuDeptMap.put("pId", studentClass.getStuParentId());
                stuDeptMap.put("name", studentClass.getStuDeptName());
                stuDeptMap.put("title", studentClass.getStuDeptName());
                if (isCheck)
                {
                    stuDeptMap.put("checked", rolestuDeptList.contains(studentClass.getStuDeptId() + studentClass.getStuDeptName()));
                }
                else
                {
                    stuDeptMap.put("checked", false);
                }
                trees.add(stuDeptMap);
            }
        }
        return trees;
    }

    /*
     * 添加学校、年级、班级信息
     *
     * @param studentClass 要添加的标签信息
     * *//*
    @Override
    public void insertStudentClass(StudentClass studentClass) {
        studentClassMapper.insertSelective(studentClass);
    }
    *//*
     * 根据标签ID删除标签信息
     *
     * @param studentClassId 标签ID
     * *//*
    @Override
    public void deleteClassByClassId(Integer studentClassId) {
        studentClassMapper.deleteByPrimaryKey(studentClassId);
    }
    *//*
     * 根据标签ID修改标签信息
     *
     * @param studentClassId 标签ID
     * *//*
    @Override
    public void updateClassByClassId(StudentClass studentClass) {
        studentClassMapper.updateByPrimaryKeySelective(studentClass);
    }
    *//*
     * 根据标签ID查询
     *
     * @param studentClassId 标签ID
     * @return 返回一个标签对象
     * *//*
    @Override
    public StudentClass selectClassByClassId(Integer studentClassId) {
        StudentClass studentClass = studentClassMapper.selectByPrimaryKey(studentClassId);
        return studentClass;
    }
    *//*
     * 根据机构进行模糊查询
     *
     * @param condition 接受到的查询条件
     * @return 返回标签对象集合
     * *//*
    @Override
    public List<StudentClass> selectStudentClassByCondition(String condition) {
        condition = "%" + condition + "%";
        Example example = new Example(StudentClass.class);
        example.createCriteria().andLike("stuDeptName", condition);
        List<StudentClass> studentClasses = studentClassMapper.selectByExample(example);
        return studentClasses;
    }
    *//*
     * 根据stuDeptId查询该标签下的所有班级Id
     *
     * @param stuDeptId 接收到的标签
     * @return 返回一个Integer的stuDeptId的集合 表示所有的班级的id
     * *//*
    @Override
    public List<StudentClass> selectClassIdByStuDeptId(Integer stuDeptId) {
        List<StudentClass> list = new ArrayList<>();
        //首先确定该标签的等级
        StudentClass studentClass = studentClassMapper.selectByPrimaryKey(stuDeptId);
        String[] split = studentClass.getStuAncestors().split(",");
        //如果为第三等，则直接返回
        if (split.length == 3) {
            StudentClass aClass = studentClassMapper.selectByPrimaryKey(stuDeptId);
            list.add(aClass);
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
    *//*
     * 查询所有机构信息
     *
     * @return 返回全部学校和年级班级信息
     * *//*
    @Override
    public List<StudentClass> selectAllTree() {
        //首先查询所有一级标签:学校
        List<StudentClass> list = null;
        Example example1 = new Example(StudentClass.class);
        example1.createCriteria().andEqualTo("stuParentId", "0");
        List<StudentClass> schools = studentClassMapper.selectByExample(example1);
        //遍历查询一级标签下的二级标签：年级
        schools.forEach((s)->{
            Example example2 = new Example(StudentClass.class);
            example2.createCriteria().andEqualTo("stuParentId", s.getStuDeptId());
            List<StudentClass> grade = studentClassMapper.selectByExample(example2);
            s.setStudentClasses(grade);
            //遍历查询二级标签下的三级标签：班级
            grade.forEach((g)->{
                List<StudentClass> classes = selectClassIdByStuDeptId(g.getStuDeptId());
                g.setStudentClasses(classes);
            });
        });
        list = schools;

        return list;
    }*/
}

package com.uustop.project.system.dept.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.uustop.project.system.dept.domain.Dept;

/**
 * 组织机构管理 数据层
 * 
 * @author uustop
 */
public interface DeptMapper
{
    /**
     * 查询组织机构人数
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int selectDeptCount(Dept dept);

    /**
     * 查询组织机构是否存在用户
     * 
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询组织机构管理数据
     * 
     * @param dept 组织机构信息
     * @return 组织机构信息集合
     */
    public List<Dept> selectDeptList(Dept dept);

    /**
     * 删除组织机构管理信息
     * 
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int insertDept(Dept dept);

    /**
     * 修改组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int updateDept(Dept dept);

    /**
     * 修改子元素关系
     * 
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<Dept> depts);

    /**
     * 根据组织机构ID查询信息
     * 
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    public Dept selectDeptById(Long deptId);

    /**
     * 校验所属机构是否唯一
     * 
     * @param deptName 所属机构
     * @param parentId 父组织机构ID
     * @return 结果
     */
    public Dept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询组织机构
     *
     * @param roleId 角色ID
     * @return 组织机构列表
     */
    public List<String> selectRoleDeptTree(Long roleId);
}

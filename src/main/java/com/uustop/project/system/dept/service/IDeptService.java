package com.uustop.project.system.dept.service;

import java.util.List;
import java.util.Map;

import com.uustop.project.system.dept.domain.Dept;
import com.uustop.project.system.role.domain.Role;

/**
 * 组织机构管理 服务层
 * 
 * @author uustop
 */
public interface IDeptService
{
    /**
     * 查询组织机构管理数据
     * 
     * @param dept 组织机构信息
     * @return 组织机构信息集合
     */
    public List<Dept> selectDeptList(Dept dept);

    /**
     * 查询组织机构管理树
     * 
     * @return 所有组织机构信息
     */
    public List<Map<String, Object>> selectDeptTree();

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Map<String, Object>> roleDeptTreeData(Role role);

    /**
     * 查询组织机构人数
     * 
     * @param parentId 父组织机构ID
     * @return 结果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 查询组织机构是否存在用户
     * 
     * @param deptId 组织机构ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 删除组织机构管理信息
     * 
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增保存组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int insertDept(Dept dept);

    /**
     * 修改保存组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int updateDept(Dept dept);

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
     * @param dept 组织机构信息
     * @return 结果
     */
    public String checkDeptNameUnique(Dept dept);
}

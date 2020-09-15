package com.uustop.project.system.role.mapper;

import java.util.List;
import com.uustop.project.system.role.domain.RoleDept;

/**
 * 角色与组织机构关联表 数据层
 * 
 * @author uustop
 */
public interface RoleDeptMapper
{
    /**
     * 通过角色ID删除角色和组织机构关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色组织机构关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * 查询组织机构使用数量
     * 
     * @param deptId 组织机构ID
     * @return 结果
     */
    public int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色组织机构信息
     * 
     * @param roleDeptList 角色组织机构列表
     * @return 结果
     */
    public int batchRoleDept(List<RoleDept> roleDeptList);
}

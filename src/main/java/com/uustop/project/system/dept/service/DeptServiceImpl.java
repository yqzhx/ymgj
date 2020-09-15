package com.uustop.project.system.dept.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uustop.common.constant.UserConstants;
import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.security.ShiroUtils;
import com.uustop.framework.aspectj.lang.annotation.DataScope;
import com.uustop.project.system.dept.domain.Dept;
import com.uustop.project.system.dept.mapper.DeptMapper;
import com.uustop.project.system.role.domain.Role;

/**
 * 组织机构管理 服务实现
 * 
 * @author uustop
 */
@Service
public class DeptServiceImpl implements IDeptService
{
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询组织机构管理数据
     * 
     * @return 组织机构信息集合
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<Dept> selectDeptList(Dept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询组织机构管理树
     * 
     * @return 所有组织机构信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Dept> deptList = selectDeptList(new Dept());
        trees = getTrees(deptList, false, null);
        return trees;
    }

    /**
     * 根据角色ID查询组织机构（数据权限）
     *
     * @param role 角色对象
     * @return 组织机构列表（数据权限）
     */
    @Override
    public List<Map<String, Object>> roleDeptTreeData(Role role)
    {
        Long roleId = role.getRoleId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            trees = getTrees(deptList, true, roleDeptList);
        }
        else
        {
            trees = getTrees(deptList, false, null);
        }
        return trees;
    }

    /**
     * 对象转组织机构树
     *
     * @param menuList 组织机构列表
     * @param isCheck 是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Dept> deptList, boolean isCheck, List<String> roleDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Dept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Map<String, Object> deptMap = new HashMap<String, Object>();
                deptMap.put("id", dept.getDeptId());
                deptMap.put("pId", dept.getParentId());
                deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                if (isCheck)
                {
                    deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                else
                {
                    deptMap.put("checked", false);
                }
                trees.add(deptMap);
            }
        }
        return trees;
    }

    /**
     * 查询组织机构人数
     * 
     * @param parentId 组织机构ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId)
    {
        Dept dept = new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询组织机构是否存在用户
     * 
     * @param deptId 组织机构ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除组织机构管理信息
     * 
     * @param deptId 组织机构ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {

        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    @Override
    public int insertDept(Dept dept)
    {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        dept.setCreateBy(ShiroUtils.getLoginName());
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    @Override
    public int updateDept(Dept dept)
    {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        if (StringUtils.isNotNull(info))
        {
            String ancestors = info.getAncestors() + "," + dept.getParentId();
            dept.setAncestors(ancestors);
            updateDeptChildren(dept.getDeptId(), ancestors);
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 组织机构ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId, String ancestors)
    {
        Dept dept = new Dept();
        dept.setParentId(deptId);
        List<Dept> childrens = deptMapper.selectDeptList(dept);
        for (Dept children : childrens)
        {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0)
        {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据组织机构ID查询信息
     * 
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    @Override
    public Dept selectDeptById(Long deptId)
    {

        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验所属机构是否唯一
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Dept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }
}

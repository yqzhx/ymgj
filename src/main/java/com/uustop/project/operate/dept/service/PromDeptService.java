package com.uustop.project.operate.dept.service;

import com.uustop.project.operate.dept.domain.PromDep;

import java.util.List;

/**
 * 组织机构 服务层
 *
 * @author xh
 * @Date: 2019-03-13
 */
public interface PromDeptService {
    /**
     * 查询组织机构信息
     *
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    public PromDep selectDeptById(Integer deptId);

    /**
     * 查询组织机构列表
     *
     * @param dept 组织机构信息
     * @return 组织机构集合
     */
    public List<PromDep> selectDeptList(PromDep dept);

    /**
     * 新增组织机构
     *
     * @param dept 组织机构信息
     * @return 结果
     */
    public int insertDept(PromDep dept);

    /**
     * 修改组织机构
     *
     * @param dept 组织机构信息
     * @return 结果
     */
    public int updateDept(PromDep dept);

    /**
     * 删除组织机构信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeptByIds(String ids);

}

package com.uustop.project.operate.dept.service;

import java.util.List;

import com.uustop.common.support.Convert;
import com.uustop.project.operate.dept.mapper.PromDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uustop.project.operate.dept.domain.PromDep;

/**
 * 组织机构 服务层实现
 *
 * @author xh
 * @Date: 2019-03-13
 */
@Service
public class PromDeptServiceImpl implements PromDeptService {

    @Autowired
    private PromDeptMapper deptMapper;

    /**
     * 查询组织机构信息
     *
     * @param deptId 组织机构ID
     * @return 组织机构信息
     */
    @Override
    public PromDep selectDeptById(Integer deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 查询组织机构列表
     *
     * @param dept 组织机构信息
     * @return 组织机构集合
     */
    @Override
    public List<PromDep> selectDeptList(PromDep dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 新增组织机构
     *
     * @param dept 组织机构信息
     * @return 结果
     */
    @Override
    public int insertDept(PromDep dept) {
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改组织机构
     *
     * @param dept 组织机构信息
     * @return 结果
     */
    @Override
    public int updateDept(PromDep dept) {
        return deptMapper.updateDept(dept);
    }

    /**
     * 删除组织机构对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDeptByIds(String ids) {
        return deptMapper.deleteDeptByIds(Convert.toStrArray(ids));
    }

}

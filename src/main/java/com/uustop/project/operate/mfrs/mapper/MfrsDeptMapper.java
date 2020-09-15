package com.uustop.project.operate.mfrs.mapper;


import com.uustop.project.operate.mfrs.domain.MfrsDept;

/**
 * 组织机构管理 数据层
 * 
 * @author uustop
 */
public interface MfrsDeptMapper
{
   
    /**
     * 新增组织机构信息
     * 
     * @param dept 组织机构信息
     * @return 结果
     */
    public int insertDept(MfrsDept dept);

 
}

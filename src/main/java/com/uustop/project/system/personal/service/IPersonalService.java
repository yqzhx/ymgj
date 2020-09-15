package com.uustop.project.system.personal.service;

import com.uustop.project.system.personal.domain.Personal;

import java.util.List;

/**
 * 个性化功能选择 服务层
 * 
 * @author zx
 * @date 2019-07-18
 */
public interface IPersonalService 
{
	/**
     * 查询个性化功能选择信息
     * 
     * @param  个性化功能选择ID
     * @return 个性化功能选择信息
     */
	public List<Personal> selectPersonalById(Integer accountId);
	
	/**
     * 查询个性化功能选择列表
     * 
     * @param personal 个性化功能选择信息
     * @return 个性化功能选择集合
     */
	public List<Personal> selectPersonalList(Personal personal);
	
	/**
     * 新增个性化功能选择
     * 
     * @param personal 个性化功能选择信息
     * @return 结果
     */
	public int insertPersonal(Personal personal);
	
	/**
     * 修改个性化功能选择
     * 
     * @param personal 个性化功能选择信息
     * @return 结果
     */
	public int updatePersonal(Personal personal);
		
	/**
     * 删除个性化功能选择信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePersonalByIds(Integer ids);

	public List selectAllMenu(Integer roleId);

	public List selectMenuAndPicture(Integer accountId);

	public List selectMenuNotInMenu(Integer accountId, Integer roleId);
	
}

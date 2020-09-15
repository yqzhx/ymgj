package com.uustop.project.system.personal.service;

import com.uustop.project.system.personal.domain.Personal;
import com.uustop.project.system.personal.mapper.PersonalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个性化功能选择 服务层实现
 * 
 * @author zx
 * @date 2019-07-18
 */
@Service
public class PersonalServiceImpl implements IPersonalService
{
	@Autowired
	private PersonalMapper personalMapper;

	/**
     * 查询个性化功能选择信息
     * 
     * @param personalId 个性化功能选择ID
     * @return 个性化功能选择信息
     */
    @Override
	public List<Personal> selectPersonalById(Integer accountId)
	{
	    return personalMapper.selectPersonalById(accountId);
	}
	
	/**
     * 查询个性化功能选择列表
     * 
     * @param personal 个性化功能选择信息
     * @return 个性化功能选择集合
     */
	@Override
	public List selectPersonalList(Personal personal)
	{
	    return personalMapper.selectPersonalList(personal);
	}
	
    /**
     * 新增个性化功能选择
     * 
     * @param personal 个性化功能选择信息
     * @return 结果
     */
	@Override
	public int insertPersonal(Personal personal)
	{
	    return personalMapper.insertPersonal(personal);
	}
	
	/**
     * 修改个性化功能选择
     * 
     * @param personal 个性化功能选择信息
     * @return 结果
     */
	@Override
	public int updatePersonal(Personal personal)
	{
	    return personalMapper.updatePersonal(personal);
	}

	/**
     * 删除个性化功能选择对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePersonalByIds(Integer ids)
	{
		return personalMapper.deletePersonalByIds(ids);
	}

	@Override
	public List selectMenuAndPicture(Integer accountId){
		return personalMapper.selectMenuAndPicture(accountId);
	}

	@Override
	public List selectMenuNotInMenu(Integer accountId,Integer roleId){
		return personalMapper.selectMenuNotInMenu(accountId,roleId);
	}

	public List selectAllMenu(Integer roleId){
		return personalMapper.selectAllMenu(roleId);
	}
	
}

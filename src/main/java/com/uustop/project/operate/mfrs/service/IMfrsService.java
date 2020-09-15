package com.uustop.project.operate.mfrs.service;


import com.uustop.project.operate.mfrs.domain.Mfrs;

import java.util.List;

/**
 * 合作企业 服务层
 *
 * @Auther: xh
 * @Date: 2019-02-15
 */
public interface IMfrsService {
    /**
     * 查询合作企业信息
     *
     * @param mfrsId 合作企业ID
     * @return 合作企业信息
     */
    public Mfrs selectMfrsById(Integer mfrsId);

    /**
     * 查询合作企业列表
     *
     * @param mfrs 合作企业信息
     * @return 合作企业集合
     */
    public List<Mfrs> selectMfrsList(Mfrs mfrs);

    /**
     * 新增合作企业
     *
     * @param mfrs 合作企业信息
     * @return 结果
     */
    public int insertMfrs(Mfrs mfrs);

    /**
     * 修改合作企业
     *
     * @param mfrs 合作企业信息
     * @return 结果
     */
    public int updateMfrs(Mfrs mfrs);

    /**
     * 删除合作企业信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMfrsByIds(String ids);

    /**
     * 检索重复
     *
     * @param mfrs 合作企业
     * @return 结果
     */
    int selectCountByMfrsName(Mfrs mfrs);

    int selectCountByMfrsShortName(Mfrs mfrs);
    /**
     * 根据推广企业查询合作企业
     */
	public List<Mfrs> selectMfrsListByCompanyId(Integer companyId);

	public List<String> selectMfrsPic(String mfrsId, String type);

    List<Mfrs> selectMfrsListNotPass(Mfrs mfrs);

//	public List<Mfrs> selectAuditMfrsList(Mfrs mfrs);
//
//	public int applyMfrs(String ids);
//
//	public int reject(String ids);
//
//	public List<String> selectMfrsPic(String mfrsId, String type);

    /**
     * 根据条件查询
     *
     * @param mfrs 信息
     * @return 结果
     */
    Mfrs selectById(Mfrs mfrs);

}

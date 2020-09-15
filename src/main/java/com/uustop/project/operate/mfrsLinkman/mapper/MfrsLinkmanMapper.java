package com.uustop.project.operate.mfrsLinkman.mapper;


import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;

import java.util.List;

/**
 * 推广商联系人 数据层
 *
 * @author zx
 * @date 2019-05-22
 */
public interface MfrsLinkmanMapper {

    /**
     * 查询推广商联系人信息
     *
     * @param promLinkmanId 推广商联系人ID
     * @return 推广商联系人信息
     */
    public MfrsLinkman selectLinkmanById(Integer promLinkmanId);

    /**
     * 查询推广商联系人列表
     *
     * @param linkman 推广商联系人信息
     * @return 推广商联系人集合
     */
    public List<MfrsLinkman> selectLinkmanList(MfrsLinkman linkman);

    /**
     * 新增推广商联系人
     *
     * @param linkman 推广商联系人信息
     * @return 结果
     */
    public int insertLinkman(MfrsLinkman linkman);

    /**
     * 修改推广商联系人
     *
     * @param linkman 推广商联系人信息
     * @return 结果
     */
    public int updateLinkman(MfrsLinkman linkman);

    /**
     * 删除推广商联系人
     *
     * @param promLinkmanId 推广商联系人ID
     * @return 结果
     */
    public int deleteLinkmanById(Integer promLinkmanId);

    /**
     * 批量删除推广商联系人
     *
     * @param promLinkmanIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLinkmanByIds(String[] promLinkmanIds);

}
package com.uustop.project.operate.mfrsLinkman.service;


import com.uustop.common.support.Convert;
import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;
import com.uustop.project.operate.mfrsLinkman.mapper.MfrsLinkmanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推广商联系人 服务层实现
 *
 * @author zx
 * @date 2019-05-22
 */
@Service
public class MfrsLinkmanServiceImpl implements MfrsLinkmanService {

    @Autowired
    private MfrsLinkmanMapper linkmanMapper;

    /**
     * 查询推广商联系人信息
     *
     * @param promLinkmanId 推广商联系人ID
     * @return 推广商联系人信息
     */
    @Override
    public MfrsLinkman selectLinkmanById(Integer promLinkmanId) {
        return linkmanMapper.selectLinkmanById(promLinkmanId);
    }

    /**
     * 查询推广商联系人列表
     *
     * @param linkman 推广商联系人信息
     * @return 推广商联系人集合
     */
    @Override
    public List<MfrsLinkman> selectLinkmanList(MfrsLinkman linkman) {
        return linkmanMapper.selectLinkmanList(linkman);
    }

    /**
     * 新增推广商联系人
     *
     * @param linkman 推广商联系人信息
     * @return 结果
     */
    @Override
    public int insertLinkman(MfrsLinkman linkman) {
        return linkmanMapper.insertLinkman(linkman);
    }

    /**
     * 修改推广商联系人
     *
     * @param linkman 推广商联系人信息
     * @return 结果
     */
    @Override
    public int updateLinkman(MfrsLinkman linkman) {
        return linkmanMapper.updateLinkman(linkman);
    }

    /**
     * 删除推广商联系人对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLinkmanByIds(String ids) {
        return linkmanMapper.deleteLinkmanByIds(Convert.toStrArray(ids));
    }

}

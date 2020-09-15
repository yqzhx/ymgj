package com.uustop.project.operate.mfrs.service;


import com.uustop.common.support.Convert;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.project.operate.mfrs.domain.Mfrs;
import com.uustop.project.operate.mfrs.mapper.MfrsMapper;
import com.uustop.project.operate.mfrs.mapper.MfrsPrestoreMapper;

import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;
import com.uustop.project.operate.mfrsLinkman.mapper.MfrsLinkmanMapper;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作企业 服务层实现
 *
 * @Auther: xh
 * @Date: 2019-02-15
 */
@Service("mfrs")
public class MfrsServiceImpl implements IMfrsService {

    @Autowired
    private MfrsMapper mfrsMapper;
    @Autowired
    private MfrsPrestoreMapper mfrsPrestoreMapper;
    @Autowired
    private MfrsLinkmanMapper linkmanMapper;

    /**
     * 查询合作企业信息
     *
     * @param mfrsId 合作企业ID
     * @return 合作企业信息
     */
    @Override
    public Mfrs selectMfrsById(Integer mfrsId) {
        return mfrsMapper.selectMfrsById(mfrsId);
    }

    /**
     * 查询合作企业列表
     *
     * @param mfrs 合作企业信息
     * @return 合作企业集合
     */
    @Override
    public List<Mfrs> selectMfrsList(Mfrs mfrs) {
        return mfrsMapper.selectMfrsList(mfrs);
    }

    /**
     * 新增合作企业
     *
     * @param mfrs 合作企业信息
     * @return 结果
     */
    @Override
    public int insertMfrs(Mfrs mfrs) {
        int i = mfrsMapper.insertMfrs(mfrs);
        MfrsLinkman mfrsLinkman = new MfrsLinkman();
        String phoneNumber = mfrs.getPhoneNumber();
        String linkmanName = mfrs.getLinkmanName();
        if (StringUtils.isNotEmpty(phoneNumber)) {
            mfrsLinkman.setMobile(phoneNumber);
        }
        if (StringUtils.isNotEmpty(linkmanName)) {
            mfrsLinkman.setLinkName(linkmanName);
        }
        if (StringUtils.isNotEmpty(phoneNumber) || StringUtils.isNotEmpty(linkmanName)) {
            mfrsLinkman.setMfrsId(mfrs.getMfrsId());
            BaseEntityAutoUtils.autoBaseEntity(mfrsLinkman);
            linkmanMapper.insertLinkman(mfrsLinkman);
        }

        return i;
    }

    /**
     * 修改合作企业
     *
     * @param mfrs 合作企业信息
     * @return 结果
     */
    @Override
    public int updateMfrs(Mfrs mfrs) {
        return mfrsMapper.updateMfrs(mfrs);
    }

    /**
     * 删除合作企业对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMfrsByIds(String ids) {
        return mfrsMapper.deleteMfrsByIds(Convert.toStrArray(ids));
    }

    /**
     * 检索重复
     *
     * @param mfrs 合作企业
     * @return 结果
     */
    @Override
    public int selectCountByMfrsName(Mfrs mfrs) {
        Mfrs item = mfrsMapper.selectCountByMfrsName(mfrs);
        if (mfrs.getMfrsId() == null) {
            return item == null ? NumberUtils.INTEGER_ZERO : NumberUtils.INTEGER_ONE;
        }else {
            if (item != null && item.getMfrsId().intValue() == mfrs.getMfrsId().intValue()) {
                return NumberUtils.INTEGER_ZERO;
            } else {
                return item == null ? NumberUtils.INTEGER_ZERO : NumberUtils.INTEGER_ONE;
            }
        }
    }

    @Override
    public int selectCountByMfrsShortName(Mfrs mfrs) {
        Mfrs item = mfrsMapper.selectCountByMfrsShortName(mfrs);
        if (mfrs.getMfrsId() == null) {
            return item == null ? NumberUtils.INTEGER_ZERO : NumberUtils.INTEGER_ONE;
        }else {
            if (item != null && item.getMfrsId().intValue() == mfrs.getMfrsId().intValue()) {
                return NumberUtils.INTEGER_ZERO;
            } else {
                return item == null ? NumberUtils.INTEGER_ZERO : NumberUtils.INTEGER_ONE;
            }
        }
    }
    /**
     * 根据推广企业查询合作企业
     */
	@Override
	public List<Mfrs> selectMfrsListByCompanyId(Integer companyId) {
		return mfrsMapper.selectMfrsListByCompanyId(companyId);
	}

	@Override
	public List<String> selectMfrsPic(String mfrsId, String type) {
		Map<String, String> map = new HashMap<>();
		map.put("mfrsId", mfrsId);
		map.put("type", type);
		return mfrsPrestoreMapper.selectMfrsPrestorePic(map);
	}

    @Override
    public List<Mfrs> selectMfrsListNotPass(Mfrs mfrs) {
        return mfrsMapper.selectMfrsListNotPass(mfrs);
    }

    @Override
    public Mfrs selectById(Mfrs mfrs) {
        return mfrsMapper.selectById(mfrs);
    }

    //	@Override
//	public List<Mfrs> selectAuditMfrsList(Mfrs mfrs) {
//		return mfrsMapper.selectAuditMfrsList(mfrs);
//	}
//
//	@Override
//	public int applyMfrs(String ids) {
//		String[] strArray = Convert.toStrArray(ids);
//		for (String id : strArray) {
//			Mfrs mfrs = mfrsMapper.selectMfrsById(Integer.valueOf(id));
//			//查询mfrs下用户
//			Long accountId = mfrsUserRoleMapper.selectMfrsUserByMfrsId(id);
//			// 删除用户与角色关联
//	        mfrsUserRoleMapper.deleteUserRoleByAccountId(accountId);
//	        // 新增用户与角色管理
//	        Map<String, Object> map = new HashedMap<>();
//	        map.put("accountId", accountId);
//	        map.put("roleId", 10);
//	        mfrsUserRoleMapper.batchUserRole(map);
//		}
//
//		return mfrsMapper.applyMfrs(strArray);
//	}

	
//	@Override
//	public int reject(String ids) {
//		return mfrsMapper.reject(Convert.toStrArray(ids));
//	}
//
//	@Override
//	public List<String> selectMfrsPic(String mfrsId, String type) {
//		Map<String, String> map = new HashMap<>();
//		map.put("mfrsId", mfrsId);
//		map.put("type", type);
//		return mfrsMapper.selectMfrsPic(map);
//	}
	
	
}

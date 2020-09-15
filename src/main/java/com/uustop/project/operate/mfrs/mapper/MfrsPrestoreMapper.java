package com.uustop.project.operate.mfrs.mapper;


import com.uustop.project.operate.mfrs.domain.MfrsPrestore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合作企业 数据层
 *
 */
public interface MfrsPrestoreMapper {

	public List<MfrsPrestore> selectAuditMfrsPrestoreList(MfrsPrestore mfrsPrestore);

	public int applyMfrsPrestore(Integer mfrsPrestoreId);

	public int reject(Integer id);

	public List<String> selectMfrsPrestorePic(Map<String, String> map);

	public MfrsPrestore selectMfrsPrestoreById(Integer valueOf);

	public void bindingPicToMfrs(Map<String, Object> map);

	public int updateMfrsId(@Param("mfrsId") Integer mfrsId,@Param("mfrsPrestoreId") Integer mfrsPrestoreId);

	int selectMfrsIdCount(Integer mfrsId);

	List<MfrsPrestore> selectReviewAgainList(MfrsPrestore mfrsPrestore);

	public void updateMfrsById(MfrsPrestore mfrsPrestore);
}
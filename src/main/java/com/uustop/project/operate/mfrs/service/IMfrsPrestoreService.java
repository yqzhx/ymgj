package com.uustop.project.operate.mfrs.service;


import com.uustop.project.operate.mfrs.domain.MfrsPrestore;
import com.uustop.project.operate.picture.domain.Picture;

import java.util.List;

/**
 * 合作企业 服务层
 */
public interface IMfrsPrestoreService {

    public List<MfrsPrestore> selectAuditMfrsPrestoreList(MfrsPrestore mfrsPrestore);

    public int applyMfrsPrestore(Integer mfrsId, Integer mfrsPrestoreId);

    public int reject(Integer mfrsId);

    public List<String> selectMfrsPrestorePic(String mfrsPrestoreId, String type);

    List<MfrsPrestore> selectReviewAgainList(MfrsPrestore mfrsPrestore);

    int applyReviewAgain(String ids);

    List<Picture> selectReviewAgainMap(MfrsPrestore mfrsPrestore);

    List<Picture> selectReviewAgainMfrsPrestore(MfrsPrestore mfrsPrestore);

    public MfrsPrestore selectMfrsPrestoreById(Integer valueOf);

    public void updateMfrsById(MfrsPrestore mfrsPrestore);
}

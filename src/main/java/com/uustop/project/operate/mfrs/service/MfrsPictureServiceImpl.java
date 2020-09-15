package com.uustop.project.operate.mfrs.service;


import com.uustop.common.support.Convert;
import com.uustop.project.operate.mfrs.domain.MfrsPicture;
import com.uustop.project.operate.mfrs.mapper.MfrsPictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 厂商图片中间 服务层实现
 *
 * @author zx
 * @Date: 2019-05-24
 */
@Service
public class MfrsPictureServiceImpl implements IMfrsPictureService {

    @Autowired
    private MfrsPictureMapper mfrsPictureMapper;

    /**
     * 查询厂商图片中间信息
     *
     * @param mfrsPictureId 厂商图片中间ID
     * @return 厂商图片中间信息
     */
    @Override
    public MfrsPicture selectMfrsPictureById(Integer mfrsPictureId) {
        return mfrsPictureMapper.selectMfrsPictureById(mfrsPictureId);
    }

    /**
     * 查询厂商图片中间列表
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 厂商图片中间集合
     */
    @Override
    public List<MfrsPicture> selectMfrsPictureList(MfrsPicture mfrsPicture) {
        return mfrsPictureMapper.selectMfrsPictureList(mfrsPicture);
    }

    /**
     * 新增厂商图片中间
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 结果
     */
    @Override
    public int insertMfrsPicture(MfrsPicture mfrsPicture) {
        return mfrsPictureMapper.insertMfrsPicture(mfrsPicture);
    }

    /**
     * 修改厂商图片中间
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 结果
     */
    @Override
    public int updateMfrsPicture(MfrsPicture mfrsPicture) {
        return mfrsPictureMapper.updateMfrsPicture(mfrsPicture);
    }

    /**
     * 删除厂商图片中间对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMfrsPictureByIds(String ids) {
        return mfrsPictureMapper.deleteMfrsPictureByIds(Convert.toStrArray(ids));
    }

    /**
     * 根据图片类型返回图片(最新)
     *
     * @param mfrsPicture 疾控
     * @return 结果
     */
    @Override
    public List<MfrsPicture> selectPictureByMfrsIdAndTypeOther(MfrsPicture mfrsPicture) {
        return mfrsPictureMapper.selectPictureByMfrsIdAndTypeOther(mfrsPicture);
    }

    /**
     * 根据图片类型返回图片
     *
     * @param mfrsPicture 合作企业
     * @return 结果
     */
    @Override
    public List<MfrsPicture> selectPictureByMfrsIdAndType(MfrsPicture mfrsPicture) {
        return mfrsPictureMapper.selectPictureByMfrsIdAndType(mfrsPicture);
    }

}

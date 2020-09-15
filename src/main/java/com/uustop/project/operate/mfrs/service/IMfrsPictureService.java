package com.uustop.project.operate.mfrs.service;


import com.uustop.project.operate.mfrs.domain.MfrsPicture;

import java.util.List;

/**
 * 厂商图片中间 服务层
 *
 * @author zx
 * @Date: 2019-05-24
 */
public interface IMfrsPictureService {
    /**
     * 查询厂商图片中间信息
     *
     * @param mfrsPictureId 厂商图片中间ID
     * @return 厂商图片中间信息
     */
    public MfrsPicture selectMfrsPictureById(Integer mfrsPictureId);

    /**
     * 查询厂商图片中间列表
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 厂商图片中间集合
     */
    public List<MfrsPicture> selectMfrsPictureList(MfrsPicture mfrsPicture);

    /**
     * 新增厂商图片中间
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 结果
     */
    public int insertMfrsPicture(MfrsPicture mfrsPicture);

    /**
     * 修改厂商图片中间
     *
     * @param mfrsPicture 厂商图片中间信息
     * @return 结果
     */
    public int updateMfrsPicture(MfrsPicture mfrsPicture);

    /**
     * 删除厂商图片中间信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMfrsPictureByIds(String ids);


    /**
     * 根据图片类型返回图片(最新)
     *
     * @param mfrsPicture 疾控
     * @return 结果
     */
    List<MfrsPicture> selectPictureByMfrsIdAndTypeOther(MfrsPicture mfrsPicture);


    /**
     * 根据图片类型返回图片
     *
     * @param mfrsPicture 合作企业
     * @return 结果
     */
    List<MfrsPicture> selectPictureByMfrsIdAndType(MfrsPicture mfrsPicture);

}

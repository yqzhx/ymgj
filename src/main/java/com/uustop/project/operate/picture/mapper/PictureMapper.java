package com.uustop.project.operate.picture.mapper;


import com.uustop.project.operate.mfrs.domain.MfrsPrestore;
import com.uustop.project.operate.picture.domain.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图片 数据层
 *
 * @author xh
 * @Date: 2019-02-26
 */
public interface PictureMapper {
    /**
     * 查询图片信息
     *
     * @param picId 图片ID
     * @return 图片信息
     */
    public Picture selectPictureById(Integer picId);

    /**
     * 查询图片列表
     *
     * @param picture 图片信息
     * @return 图片集合
     */
    public List<Picture> selectPictureList(Picture picture);

    /**
     * 新增图片
     *
     * @param picture 图片信息
     * @return 结果
     */
    public int insertPicture(Picture picture);

    /**
     * 修改图片
     *
     * @param picture 图片信息
     * @return 结果
     */
    public int updatePicture(Picture picture);

    /**
     * 删除图片
     *
     * @param picId 图片ID
     * @return 结果
     */
    public int deletePictureById(Integer picId);

    /**
     * 批量删除图片
     *
     * @param picIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePictureByIds(String[] picIds);

    List<Picture> selectReviewPic(MfrsPrestore mfrsPrestore);

    List<Picture> selectReviewPicPrestore(MfrsPrestore mfrsPrestore);

    List<String> selectCdcPrestorePic(@Param("cdcPrestoreId") Integer cdcPrestoreId,@Param("picType") String type);

    List<Picture> selectCdcPic(@Param("cdcId") Integer cdcId,@Param("type") Integer type);

    List<Picture> selectCdcPrestorePicObj(@Param("cdcPrestoreId") Integer cdcPrestoreId,@Param("picType") String type);


}
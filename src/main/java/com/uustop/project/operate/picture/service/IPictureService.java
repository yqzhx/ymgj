package com.uustop.project.operate.picture.service;


import com.uustop.project.operate.picture.domain.Picture;

import java.util.List;

/**
 * 图片 服务层
 *
 * @author xh
 * @Date: 2019-02-26
 */
public interface IPictureService {
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
     * 删除图片信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePictureByIds(String ids);

}

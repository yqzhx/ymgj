package com.uustop.project.operate.picture.service;


import com.uustop.common.support.Convert;
import com.uustop.project.operate.picture.domain.Picture;
import com.uustop.project.operate.picture.mapper.PictureMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片 服务层实现
 *
 * @author xh
 * @Date: 2019-02-26
 */
@Service
public class PictureServiceImpl implements IPictureService {
    @Autowired
    private PictureMapper pictureMapper;

    /**
     * 查询图片信息
     *
     * @param picId 图片ID
     * @return 图片信息
     */
    @Override
    public Picture selectPictureById(Integer picId) {
        Picture picture = pictureMapper.selectPictureById(picId);
        String picFilepath = picture.getPicFilepath();
        String name = picture.getName();
        String newName = FilenameUtils.removeExtension(name);
        String suffix = FilenameUtils.getExtension(name);
        // 缩略图1
        String oneLargest = picFilepath.replace(name,
                newName + "_oneLargest" + FilenameUtils.EXTENSION_SEPARATOR + suffix);
        // 缩略图2
        String secondLargest = picFilepath.replace(name,
                newName + "_secondLargest" + FilenameUtils.EXTENSION_SEPARATOR + suffix);
        picture.setOneLargest(oneLargest);
        picture.setSecondLargest(secondLargest);
        return picture;
    }

    /**
     * 查询图片列表
     *
     * @param picture 图片信息
     * @return 图片集合
     */
    @Override
    public List<Picture> selectPictureList(Picture picture) {
        return pictureMapper.selectPictureList(picture);
    }

    /**
     * 新增图片
     *
     * @param picture 图片信息
     * @return 结果
     */
    @Override
    public int insertPicture(Picture picture) {
        return pictureMapper.insertPicture(picture);
    }

    /**
     * 修改图片
     *
     * @param picture 图片信息
     * @return 结果
     */
    @Override
    public int updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }

    /**
     * 删除图片对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePictureByIds(String ids) {
        return pictureMapper.deletePictureByIds(Convert.toStrArray(ids));
    }

}

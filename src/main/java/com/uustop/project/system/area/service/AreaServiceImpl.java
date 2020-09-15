package com.uustop.project.system.area.service;


import com.uustop.common.support.Convert;
import com.uustop.project.system.area.domain.Area;
import com.uustop.project.system.area.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 所属地区 服务层实现
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-02-27
 */
@Service
public class AreaServiceImpl implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 查询所属地区信息
     *
     * @param areaId 所属地区ID
     * @return 所属地区信息
     */
    @Override
    public Area selectAreaById(Integer areaId) {
        return areaMapper.selectAreaById(areaId);
    }

    /**
     * 查询所属地区列表
     *
     * @param area 所属地区信息
     * @return 所属地区集合
     */
    @Override
    public List<Area> selectAreaList(Area area) {
        return areaMapper.selectAreaList(area);
    }

    /**
     * 新增所属地区
     *
     * @param area 所属地区信息
     * @return 结果
     */
    @Override
    public int insertArea(Area area) {
        return areaMapper.insertArea(area);
    }

    /**
     * 修改所属地区
     *
     * @param area 所属地区信息
     * @return 结果
     */
    @Override
    public int updateArea(Area area) {
        return areaMapper.updateArea(area);
    }

    /**
     * 删除所属地区对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAreaByIds(String ids) {
        return areaMapper.deleteAreaByIds(Convert.toStrArray(ids));
    }

    /**
     * 所属地区查重
     *
     * @param areaName 所属地区名称
     * @return 结果
     */
    @Override
    public int selectCountByAreaName(String areaName) {
        return areaMapper.selectCountByAreaName(areaName);
    }
}

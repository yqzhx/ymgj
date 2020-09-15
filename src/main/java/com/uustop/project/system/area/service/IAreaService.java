package com.uustop.project.system.area.service;


import com.uustop.project.system.area.domain.Area;

import java.util.List;

/**
 * 所属地区 服务层
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-02-27
 */
public interface IAreaService {
    /**
     * 查询所属地区信息
     *
     * @param areaId 所属地区ID
     * @return 所属地区信息
     */
    public Area selectAreaById(Integer areaId);

    /**
     * 查询所属地区列表
     *
     * @param area 所属地区信息
     * @return 所属地区集合
     */
    public List<Area> selectAreaList(Area area);

    /**
     * 新增所属地区
     *
     * @param area 所属地区信息
     * @return 结果
     */
    public int insertArea(Area area);

    /**
     * 修改所属地区
     *
     * @param area 所属地区信息
     * @return 结果
     */
    public int updateArea(Area area);

    /**
     * 删除所属地区信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAreaByIds(String ids);

    /**
     * 所属地区查重
     *
     * @param areaName 所属地区名称
     * @return 结果
     */
    int selectCountByAreaName(String areaName);

}

package com.uustop.project.system.area.controller;


import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.system.area.domain.Area;
import com.uustop.project.system.area.service.IAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 所属地区 信息操作处理
 *
 * @author xh
 * @Date: 2019-02-27
 */
@Controller
@RequestMapping("/system/area")
public class AreaController extends BaseController {
    private String prefix = "system/area";

    @Autowired
    private IAreaService areaService;

//    @RequiresPermissions("system:area:view")
    @GetMapping()
    public String area() {
        return prefix + "/area";
    }

    /**
     * 查询所属地区列表
     */
//    @RequiresPermissions("system:area:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Area area) {
        startPage();
        List<Area> list = areaService.selectAreaList(area);
        return getDataTable(list);
    }


    /**
     * 导出所属地区列表
     */
//    @RequiresPermissions("system:area:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Area area) {
        List<Area> list = areaService.selectAreaList(area);
        ExcelUtil<Area> util = new ExcelUtil<Area>(Area.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 新增所属地区
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存所属地区
     */
//    @RequiresPermissions("system:area:add")
    @Log(title = "所属地区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Area area) {
        Integer level = area.getLevel();
        area.setCreateBy(getLoginName());
        area.setCreateTime(new Date());
        area.setUpdateTime(new Date());
        area.setUpdateBy(getLoginName());
        area.setFirst(area.getFirst().toUpperCase());
        area.setPinyin(area.getPinyin().toLowerCase());
        if (level == 1) {
            areaService.insertArea(area);
            area.setTree(area.getAreaId().toString());
        } else if (level == 2) {
            areaService.insertArea(area);
            area.setTree(area.getPid() + "," + area.getAreaId());
        } else {
            Area item = areaService.selectAreaById(area.getPid());
            areaService.insertArea(area);
            area.setTree(item.getPid() + "," + area.getPid() + "," + area.getAreaId());
        }
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 修改所属地区
     */
    @GetMapping("/edit/{areaId}")
    public String edit(@PathVariable("areaId") Integer areaId, ModelMap mmap) {
        Area area = areaService.selectAreaById(areaId);
        if (area != null) {
            Integer level = area.getLevel();
            if (level == 2) {
                mmap.put("cityId", area.getTree().split(",")[1]);
            } else if (level == 3) {
                mmap.put("cityId", area.getTree().split(",")[1]);
                mmap.put("countyId", area.getTree().split(",")[2]);
            }
            mmap.put("provinceId", area.getTree().split(",")[0]);
        }
        mmap.put("area", area);
        return prefix + "/edit";
    }

    /**
     * 修改保存所属地区
     */
//    @RequiresPermissions("system:area:edit")
    @Log(title = "所属地区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Area area) {
        Area old = areaService.selectAreaById(area.getAreaId());
        if (!StringUtils.equals(area.getName(), old.getName())) {
            int count = areaService.selectCountByAreaName(area.getName());
            if (count > 0) {
                return AjaxResult.error("所属地区已经存在");
            }
        }
        area.setFirst(area.getFirst().toUpperCase());
        area.setPinyin(area.getPinyin().toLowerCase());
        area.setUpdateBy(getLoginName());
        area.setUpdateTime(new Date());
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 删除所属地区
     */
//    @RequiresPermissions("system:area:remove")
    @Log(title = "所属地区", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(areaService.deleteAreaByIds(ids));
    }

    /**
     * 检索合作企业
     */
    @PostMapping("/checkAreaNameUnique")
    @ResponseBody
    public int check(String name) {
        return areaService.selectCountByAreaName(name);
    }
}

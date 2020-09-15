package com.uustop.project.operate.picture.controller;


import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.operate.picture.domain.Picture;
import com.uustop.project.operate.picture.service.IPictureService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片 信息操作处理
 *
 * @author xh
 * @Date: 2019-02-26
 */
@Controller
@RequestMapping("/operate/picture")
public class PictureController extends BaseController {
    private String prefix = "operate/picture";

    @Autowired
    private IPictureService pictureService;

    @RequiresPermissions("operate:picture:view")
    @GetMapping()
    public String picture() {
        return prefix + "/picture";
    }

    /**
     * 查询图片列表
     */
    @RequiresPermissions("operate:picture:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Picture picture) {
        startPage();
        List<Picture> list = pictureService.selectPictureList(picture);
        return getDataTable(list);
    }

    /**
     * 查询图片
     */
    @PostMapping("/selectPictureById")
    @ResponseBody
    public AjaxResult selectPictureById(String picIds) {
        List<Picture> pictures = new ArrayList<>();
        if (StringUtils.isNotEmpty(picIds)) {
            String[] split = picIds.split(",");
            for (String id : split) {
                Picture picture = pictureService.selectPictureById(Integer.parseInt(id));
                pictures.add(picture);
            }
        }

        return AjaxResult.success(pictures);
    }
    /**
     * 导出图片列表
     */
    @RequiresPermissions("operate:picture:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Picture picture) {
        List<Picture> list = pictureService.selectPictureList(picture);
        ExcelUtil<Picture> util = new ExcelUtil<Picture>(Picture.class);
        return util.exportExcel(list, "picture");
    }

    /**
     * 新增图片
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存图片
     */
    @RequiresPermissions("operate:picture:add")
    @Log(title = "图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Picture picture) {
        return toAjax(pictureService.insertPicture(picture));
    }

    /**
     * 修改图片
     */
    @GetMapping("/edit/{picId}")
    public String edit(@PathVariable("picId") Integer picId, ModelMap mmap) {
        Picture picture = pictureService.selectPictureById(picId);
        mmap.put("picture", picture);
        return prefix + "/edit";
    }

    /**
     * 修改保存图片
     */
    @RequiresPermissions("operate:picture:edit")
    @Log(title = "图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Picture picture) {
        return toAjax(pictureService.updatePicture(picture));
    }

    /**
     * 删除图片
     */
    @RequiresPermissions("operate:picture:remove")
    @Log(title = "图片", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(pictureService.deletePictureByIds(ids));
    }

}

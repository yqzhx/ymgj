package com.uustop.project.operate.mfrsLinkman.controller;


import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;
import com.uustop.project.operate.mfrsLinkman.service.MfrsLinkmanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推广商联系人 信息操作处理
 *
 * @author zx
 * @date 2019-05-22
 */
@Controller
@RequestMapping("/business/mfrsLinkman")
public class MfrsLinkmanController extends BaseController {

    private String prefix = "business/linkman";

    @Autowired
    private MfrsLinkmanService linkmanService;

    @RequiresPermissions("business:linkman:view")
    @GetMapping()
    public String linkman() {
        return prefix + "/linkman";
    }

    /**
     * 查询推广商联系人列表
     */
    @RequiresPermissions("business:linkman:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MfrsLinkman linkman) {
        startPage();
        List<MfrsLinkman> list = linkmanService.selectLinkmanList(linkman);
        return getDataTable(list);
    }


    /**
     * 导出推广商联系人列表
     */
    @RequiresPermissions("business:linkman:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MfrsLinkman linkman) {
        List<MfrsLinkman> list = linkmanService.selectLinkmanList(linkman);
        ExcelUtil<MfrsLinkman> util = new ExcelUtil<MfrsLinkman>(MfrsLinkman.class);
        return util.exportExcel(list, "linkman");
    }

    /**
     * 新增推广商联系人
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存推广商联系人
     */
    @RequiresPermissions("business:linkman:add")
    @Log(title = "推广商联系人", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MfrsLinkman linkman) {
        BaseEntityAutoUtils.autoBaseEntity(linkman);
        return toAjax(linkmanService.insertLinkman(linkman));
    }

    /**
     * 修改推广商联系人
     */
    @GetMapping("/edit/{promLinkmanId}")
    public String edit(@PathVariable("promLinkmanId") Integer promLinkmanId, ModelMap mmap) {
        MfrsLinkman linkman = linkmanService.selectLinkmanById(promLinkmanId);
        mmap.put("linkman", linkman);
        return prefix + "/edit";
    }

    /**
     * 修改保存推广商联系人
     */
    @RequiresPermissions("business:linkman:edit")
    @Log(title = "推广商联系人", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MfrsLinkman linkman) {
        BaseEntityAutoUtils.autoUpdateBaseEntity(linkman);
        return toAjax(linkmanService.updateLinkman(linkman));
    }

    /**
     * 删除推广商联系人
     */
    @RequiresPermissions("business:linkman:remove")
    @Log(title = "推广商联系人", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(linkmanService.deleteLinkmanByIds(ids));
    }

}

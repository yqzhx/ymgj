package com.uustop.project.operate.userRole.controller;

import java.util.List;

import com.uustop.project.operate.userRole.service.PromUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.project.operate.userRole.domain.PromUserRole;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.common.utils.poi.ExcelUtil;

/**
 * 用户和角色关联 信息操作处理
 *
 * @author xh
 * @Date: 2019-03-13
 */
@Controller
@RequestMapping("/operate/userRole")
public class PromUserRoleController extends BaseController {
    private String prefix = "operate/userRole";

    @Autowired
    private PromUserRoleService userRoleService;

    @RequiresPermissions("operate:userRole:view")
    @GetMapping()
    public String userRole() {
        return prefix + "/userRole";
    }

    /**
     * 查询用户和角色关联列表
     */
    @RequiresPermissions("operate:userRole:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PromUserRole userRole) {
        startPage();
        List<PromUserRole> list = userRoleService.selectUserRoleList(userRole);
        return getDataTable(list);
    }


    /**
     * 导出用户和角色关联列表
     */
    @RequiresPermissions("operate:userRole:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PromUserRole userRole) {
        List<PromUserRole> list = userRoleService.selectUserRoleList(userRole);
        ExcelUtil<PromUserRole> util = new ExcelUtil<PromUserRole>(PromUserRole.class);
        return util.exportExcel(list, "userRole");
    }

    /**
     * 新增用户和角色关联
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户和角色关联
     */
    @RequiresPermissions("operate:userRole:add")
    @Log(title = "用户和角色关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PromUserRole userRole) {
        return toAjax(userRoleService.insertUserRole(userRole));
    }

    /**
     * 修改用户和角色关联
     */
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") Integer accountId, ModelMap mmap) {
        PromUserRole userRole = userRoleService.selectUserRoleById(accountId);
        mmap.put("userRole", userRole);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户和角色关联
     */
    @RequiresPermissions("operate:userRole:edit")
    @Log(title = "用户和角色关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PromUserRole userRole) {
        return toAjax(userRoleService.updateUserRole(userRole));
    }

    /**
     * 删除用户和角色关联
     */
    @RequiresPermissions("operate:userRole:remove")
    @Log(title = "用户和角色关联", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userRoleService.deleteUserRoleByIds(ids));
    }

}

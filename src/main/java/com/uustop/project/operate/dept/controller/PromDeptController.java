package com.uustop.project.operate.dept.controller;

import java.util.List;

import com.uustop.project.operate.dept.service.PromDeptService;
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
import com.uustop.project.operate.dept.domain.PromDep;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.common.utils.poi.ExcelUtil;

/**
 * 组织机构 信息操作处理
 *
 * @author xh
 * @Date: 2019-03-13
 */
@Controller
@RequestMapping("/operate/dept")
public class PromDeptController extends BaseController {
    private String prefix = "operate/dept";

    @Autowired
    private PromDeptService deptService;

    @RequiresPermissions("operate:dept:view")
    @GetMapping()
    public String dept() {
        return prefix + "/dept";
    }

    /**
     * 查询组织机构列表
     */
    @RequiresPermissions("operate:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PromDep dept) {
        startPage();
        List<PromDep> list = deptService.selectDeptList(dept);
        return getDataTable(list);
    }


    /**
     * 导出组织机构列表
     */
    @RequiresPermissions("operate:dept:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PromDep dept) {
        List<PromDep> list = deptService.selectDeptList(dept);
        ExcelUtil<PromDep> util = new ExcelUtil<PromDep>(PromDep.class);
        return util.exportExcel(list, "dept");
    }

    /**
     * 新增组织机构
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存组织机构
     */
    @RequiresPermissions("operate:dept:add")
    @Log(title = "组织机构", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PromDep dept) {
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改组织机构
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Integer deptId, ModelMap mmap) {
        PromDep dept = deptService.selectDeptById(deptId);
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 修改保存组织机构
     */
    @RequiresPermissions("operate:dept:edit")
    @Log(title = "组织机构", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PromDep dept) {
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除组织机构
     */
    @RequiresPermissions("operate:dept:remove")
    @Log(title = "组织机构", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(deptService.deleteDeptByIds(ids));
    }

}

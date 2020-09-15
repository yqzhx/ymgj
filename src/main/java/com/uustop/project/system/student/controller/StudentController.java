package com.uustop.project.system.student.controller;

import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.system.role.service.IRoleService;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.service.StudentPersonnelService;
import com.uustop.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/student")
public class StudentController extends BaseController {
    private String prefix = "system/student" ;
    @Autowired
    private StudentPersonnelService studentPersonnelService;
    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("system:student:view")
    @GetMapping()
    public String student()
    {
        return prefix + "/student";
    }

    @RequiresPermissions("system:student:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StudentPersonnel student)
    {
        startPage();
        List<StudentPersonnel> list = studentPersonnelService.selectStudentList(student);
        return getDataTable(list);
    }

    @Log(title = "学生管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:student:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StudentPersonnel student)
    {
        List<StudentPersonnel> list = studentPersonnelService.selectStudentList(student);
        ExcelUtil<StudentPersonnel> util = new ExcelUtil<StudentPersonnel>(StudentPersonnel.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:student:add")
    @Log(title = "学生管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(StudentPersonnel student)
    {
        if (StringUtils.isNotNull(student.getStuUserId()) && User.isAdmin(student.getStuUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(studentPersonnelService.insertStudent(student));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{stuUserId}")
    public String edit(@PathVariable("stuUserId") Long stuUserId, ModelMap mmap)
    {
        mmap.put("user", studentPersonnelService.selectStudentById(stuUserId));
        mmap.put("roles", roleService.selectRolesByUserId(stuUserId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:student:edit")
    @Log(title = "学生管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(StudentPersonnel student)
    {
        if (StringUtils.isNotNull(student.getStuUserId()) && User.isAdmin(student.getStuUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(studentPersonnelService.updateStudent(student));
    }

    @RequiresPermissions("system:student:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{stuUserId}")
    public String resetPwd(@PathVariable("stuUserId") Long stuUserId, ModelMap mmap)
    {
        mmap.put("student", studentPersonnelService.selectStudentById(stuUserId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:student:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(StudentPersonnel student)
    {
        return toAjax(studentPersonnelService.resetStudentPwd(student));
    }

    @RequiresPermissions("system:student:remove")
    @Log(title = "学生管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(studentPersonnelService.deleteStudentByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验账号名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(StudentPersonnel student)
    {
        return studentPersonnelService.checkLoginNameUnique(student.getStuLoginName());
    }

    /**
     * 校验手机号
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(StudentPersonnel student)
    {
        return studentPersonnelService.checkPhoneUnique(student);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(StudentPersonnel student)
    {
        return studentPersonnelService.checkEmailUnique(student);
    }


    @Log(title = "学生管理", businessType = BusinessType.STATUS)
    @RequiresPermissions("system:student:status")
    @PostMapping("/status/{stuUserId}")
    @ResponseBody
    public AjaxResult status(@PathVariable("stuUserId") Long stuUserId)
    {
        try
        {
            return toAjax(studentPersonnelService.statusStudentById(stuUserId));
        }catch (Exception e)
        {
            return error(e.getMessage());
        }
    }
}

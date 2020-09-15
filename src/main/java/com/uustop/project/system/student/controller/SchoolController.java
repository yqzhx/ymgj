package com.uustop.project.system.student.controller;

import com.uustop.common.utils.StringUtils;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.service.StudentClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/school")
public class SchoolController extends BaseController {
    private String prefix = "system/school";

    @Autowired
    private StudentClassService studentClassService;

    @RequiresPermissions("system:school:view")
    @GetMapping()
    public String school() {
        return prefix + "/school";
    }

    @RequiresPermissions("system:school:list")
    @GetMapping("/list")
    @ResponseBody
    public List<StudentClass> list(StudentClass school) {
        return studentClassService.selectStudentClassList(school);
    }

    /**
     * 新增组织机构
     * */


    @GetMapping("/add/{stuParentId}")
    public String add(@PathVariable("stuParentId") Long stuParentId, ModelMap mmap) {
        mmap.put("school", studentClassService.selectStudentClassById(stuParentId));
        return prefix + "/add";
    }

    /**
     * 新增保存组织机构
     * */


    @Log(title = "学校组织机构管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:school:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StudentClass school) {
        return toAjax(studentClassService.insertStudentClass(school));
    }

    /**
     * 修改
     * */


    @GetMapping("/edit/{stuDeptId}")
    public String edit(@PathVariable("stuDeptId") Long stuDeptId, ModelMap mmap) {
        StudentClass school = studentClassService.selectStudentClassById(stuDeptId);
        if (StringUtils.isNotNull(school) && 100L == stuDeptId) {
            school.setStuDeptName("无");
        }
        mmap.put("school", school);
        return prefix + "/edit";
    }

    /**
     * 保存
     * */


    @Log(title = "学校组织机构管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:school:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StudentClass school) {
        return toAjax(studentClassService.updateStudentClass(school));
    }

    /**
     * 删除
     * */


    @Log(title = "学校组织机构管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:school:remove")
    @PostMapping("/remove/{stuDeptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("stuDeptId") Long stuDeptId) {
        if (studentClassService.selectSchoolClassCount(stuDeptId) > 0) {
            return error(1, "存在下级组织机构,不允许删除");
        }
        if (studentClassService.checkStudentClassExistUser(stuDeptId)) {
            return error(1, "组织机构存在用户,不允许删除");
        }
        return toAjax(studentClassService.deleteStudentClassById(stuDeptId));
    }

    /**
     * 校验所属机构
     * */



    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(StudentClass school) {
        return studentClassService.checkSchoolClassNameUnique(school);
    }

    /**
     * 选择组织机构树
     * */


    @GetMapping("/selectStuDeptTree/{stuDeptId}")
    public String selectStuDeptTree(@PathVariable("stuDeptId") Long stuDeptId, ModelMap mmap) {
        mmap.put("school", studentClassService.selectStudentClassById(stuDeptId));
        return prefix + "/tree";
    }

    /**
     * 加载组织机构列表树
     * */


    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData() {
        return studentClassService.selectStudentClassTree();
    }

    /**
     * 加载角色组织机构（数据权限）列表树
     * */


    @GetMapping("/roleStuDeptTreeData")
    @ResponseBody
    public List<Map<String, Object>> roleStuDeptTreeData(StudentPersonnel student) {
        return studentClassService.roleStudentClassTreeData(student);
    }
}

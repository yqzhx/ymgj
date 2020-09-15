package com.uustop.project.system.personal.controller;

import com.alibaba.fastjson.JSONArray;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.project.system.personal.domain.Personal;
import com.uustop.project.system.personal.domain.Result;
import com.uustop.project.system.personal.service.IPersonalService;
import com.uustop.project.system.role.domain.Role;
import com.uustop.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 个性化功能选择 信息操作处理
 *
 * @author zx
 * @date 2019-07-18
 */
@Controller
@RequestMapping("/business/personal")
public class PersonalController extends BaseController {
    private String prefix = "business/personal";

    @Autowired
    private IPersonalService personalService;

    @RequiresPermissions("business:personal:view")
    @GetMapping()
    public String personal() {
        return prefix + "/personal";
    }

    /**
     * 查询个性化功能选择列表
     */
    //@RequiresPermissions("business:personal:list")
    @PostMapping("/listNotInMenu")
    @ResponseBody
    public List<Result> listNotInMenu() {
        //personal.setAccountId(getSysUser().getAccountId());
        String url="http://192.168.1.55";
        Integer userId=getSysUser().getUserId().intValue();
        User user=getSysUser();
        List<Role> roles = user.getRoles();
        Integer roleId = 1;
        if (!"admin".equals(user.getLoginName())) {
            roleId = roles.get(0).getRoleId().intValue();
        }
        List<Personal>list=personalService.selectMenuAndPicture(getSysUser().getUserId().intValue());
        List<Result> resultList = new ArrayList<>();
        if (list.size()<=0) {
            //list = personalService.selectAllMenu(roleId);
            return resultList;
        }else{
            list.clear();
            list = personalService.selectMenuNotInMenu(userId,roleId);
        }
        for (Personal personals : list) {
            Integer parentId = personals.getParentId().intValue();
            Integer menuID = personals.getMenuId().intValue();
            if (resultList == null || resultList.size() == 0) {
                Result result = new Result();
                if (personals.getParentId() == 0) {
                    result.setParentId(personals.getParentId());
                    result.setMenuId(personals.getMenuId().intValue());
                    result.setMenuName(personals.getMenuName());
                    List<Personal> personalList = new ArrayList<>();
                    result.setData(personalList);
                    resultList.add(result);
                }
            } else {
                for (Result result : resultList) {
                    if (parentId == 0) {
                        Result result1 = new Result();
                        result1.setParentId(personals.getParentId());
                        result1.setMenuId(personals.getMenuId().intValue());
                        result1.setMenuName(personals.getMenuName());
                        List<Personal> personalList = new ArrayList<>();
                        result1.setData(personalList);
                        resultList.add(result1);
                        break;
                    }
                    if (result.getMenuId().equals(parentId)) {
                        Personal personal1 = new Personal();
                        personal1.setParentId(personals.getParentId().intValue());
                        personal1.setMenuId(personals.getMenuId().intValue());
                        personal1.setMenuName(personals.getMenuName());
                        personal1.setUrl(personals.getUrl());
                        personal1.setPicId(personals.getPicId());
                        if (personals.getPicPath() != null) {
                            personal1.setPicPath(url.concat(personals.getPicPath()));
                        }
                        List<Personal> date = new ArrayList<>();
                        if (result.getData() == null) {
                            date.add(personal1);
                            result.setData(date);
                        } else {
                            date = result.getData();
                            date.add(personal1);
                            result.setData(date);
                        }
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
        return resultList;
    }

    //@RequiresPermissions("business:personal:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Result> list(Personal personal) {
        String url = "http://192.168.1.55";
        User user = getSysUser();
        List<Personal> list = personalService.selectMenuAndPicture(getSysUser().getUserId().intValue());
        List<Result> resultList = new ArrayList<>();
        List<Role> roles = user.getRoles();
        Integer roleId = 1;
        if (!"admin".equals(user.getLoginName())) {
            roleId = roles.get(0).getRoleId().intValue();
        }
        if (list.size() <= 0) {
            list = personalService.selectAllMenu(roleId);
        }
            for (Personal personals : list) {
                Integer parentId = personals.getParentId().intValue();
                Integer menuID = personals.getMenuId().intValue();
                if (resultList == null || resultList.size() == 0) {
                    Result result = new Result();
                    if (personals.getParentId().intValue() == 0) {
                        result.setParentId(personals.getParentId());
                        result.setMenuId(personals.getMenuId());
                        result.setMenuName(personals.getMenuName());
                        List<Personal> personalList = new ArrayList<>();
                        result.setData(personalList);
                        resultList.add(result);
                    }
                } else {
                    for (Result result : resultList) {
                        if (parentId == 0) {
                            Result result1 = new Result();
                            result1.setParentId(personals.getParentId());
                            result1.setMenuId(personals.getMenuId());
                            result1.setMenuName(personals.getMenuName());
                            List<Personal> personalList = new ArrayList<>();
                            result1.setData(personalList);
                            resultList.add(result1);
                            break;
                        }
                        if (result.getMenuId().equals(parentId)) {
                            Personal personal1 = new Personal();
                            personal1.setParentId(personals.getParentId());
                            personal1.setMenuId(personals.getMenuId());
                            personal1.setMenuName(personals.getMenuName());
                            personal1.setUrl(personals.getUrl());
                            personal1.setPicId(personals.getPicId());
                            if (personals.getPicPath() != null) {
                                personal1.setPicPath(url.concat(personals.getPicPath()));
                            }
                            List<Personal> date = new ArrayList<>();
                            if (result.getData() == null) {
                                date.add(personal1);
                                result.setData(date);
                            } else {
                                date = result.getData();
                                date.add(personal1);
                                result.setData(date);
                            }
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
            return resultList;
        }




    /**
     * 新增个性化功能选择
     */
    @GetMapping("/add")
    public String add(){
        return prefix + "/add";
    }

    /**
     * 新增保存个性化功能选择
     */
    //@RequiresPermissions("business:personal:add")
    @Log(title = "个性化功能选择", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("addKey") String personal) {
        List<Personal> personalList = JSONArray.parseArray(personal, Personal.class);
        personalService.deletePersonalByIds(getSysUser().getUserId().intValue());
        for (Personal personal1 :personalList){
            personal1.setUserId(getSysUser().getUserId().intValue());
            personal1.setDeptId(getSysUser().getDeptId().intValue());

            personalService.insertPersonal(personal1);
        }
        return toAjax(1);
    }

    /**
     * 修改个性化功能选择
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        List<Personal> personal = personalService.selectPersonalById(getSysUser().getUserId().intValue());
        mmap.put("personal",personal);
        return prefix + "/edit";
    }

    /**
     * 修改保存个性化功能选择
     */
    @RequiresPermissions("business:personal:edit")
    @Log(title = "个性化功能选择", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestParam("addKey") String personal) {
        List<Personal> personalList = JSONArray.parseArray(personal, Personal.class);
        for (Personal personal1 :personalList){
            personalService.updatePersonal(personal1);
        }
        return toAjax(1);
    }

    /**
     * 删除个性化功能选择
     */
    @RequiresPermissions("business:personal:remove")
    @Log(title = "个性化功能选择", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer ids) {
        return toAjax(personalService.deletePersonalByIds(ids));
    }


}

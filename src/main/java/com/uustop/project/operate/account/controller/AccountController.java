package com.uustop.project.operate.account.controller;


import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.DateUtils;
import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.common.utils.security.ShiroUtils;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.shiro.service.PasswordService;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.operate.account.domain.Account;
import com.uustop.project.operate.account.service.IAccountService;
import com.uustop.project.operate.userRole.domain.PromUserRole;
import com.uustop.project.operate.userRole.service.PromUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户 信息操作处理
 *
 * @Auther: xh
 * @Date: 2019-02-20
 */
@Controller
@RequestMapping("/operate/account")
public class AccountController extends BaseController {
    private String prefix = "operate/account";

    @Autowired
    @Qualifier("operateAccount")
    private IAccountService accountService;


    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PromUserRoleService userRoleService;

    //    @RequiresPermissions("operate:account:view")
    @GetMapping()
    public String account() {
        return prefix + "/account";
    }

    /**
     * 查询用户列表
     */
//    @RequiresPermissions("operate:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Account account) {
        startPage();
        List<Account> list = accountService.selectAccountList(account);
        return getDataTable(list);
    }


    /**
     * 导出用户列表
     */
//    @RequiresPermissions("operate:account:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Account account) {
        List<Account> list = accountService.selectAccountList(account);
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        return util.exportExcel(list, "account");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
//    @RequiresPermissions("operate:account:add")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Account account) {
        //判断用户是否存在
        String loginName = account.getLoginName();
        int byLoginName = accountService.selectByLoginName(loginName);
        if (byLoginName > 0) {
            return AjaxResult.error("该账户已经存在");
        }
        //一个企业只能有一个普通管理员
        Account par = new Account();
        par.setUserType("2");
        par.setCompanyId(account.getCompanyId());
        List<Account> accountList = accountService.selectAccountList(par);
        if (accountList.size() > 0) {
            return AjaxResult.error("一个企业只能添加一个普通管理员");
        }
        BaseEntityAutoUtils.autoBaseEntity(account);
        account.randomSalt();
        account.setPassword(passwordService.encryptPassword(account.getLoginName(),
                account.getPassword(), account.getSalt()));
        account.setLoginIp(ShiroUtils.getIp());//远程ip
        account.setLoginDate(DateUtils.getNowDate());
        accountService.insertAccount(account);
        PromUserRole promUserRole = new PromUserRole();
        promUserRole.setAccountId(account.getAccountId());
        promUserRole.setRoleId(2);
        return toAjax(userRoleService.insertUserRole(promUserRole));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") Integer accountId, ModelMap mmap) {
        Account account = accountService.selectAccountById(accountId);
        mmap.put("account", account);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
//    @RequiresPermissions("operate:account:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Account account) {
        Account acc = accountService.selectAccountById(account.getAccountId());
        //判断被更新用户是否存在
        if (acc == null) {
            return AjaxResult.error("更新用户不存在");
        }
        //更新时判断用户是否存在
        if (!StringUtils.equals(acc.getLoginName(), account.getLoginName())) {
            String loginName = account.getLoginName();
            BaseEntityAutoUtils.autoUpdateBaseEntity(account);
            int byLoginName = accountService.selectByLoginName(loginName);
            if (byLoginName > 0) {
                return AjaxResult.error("该账户已经存在");
            }
        }
        return AjaxResult.success(accountService.updateAccount(account));
    }


    /**
     * 删除用户
     */
//    @RequiresPermissions("operate:account:remove")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountService.deleteAccountByIds(ids));
    }

    /**
     * 检索登录名重复
     */
    @PostMapping("/selectCountByLoginName")
    @ResponseBody
    public int check(String name) {
        return accountService.selectByLoginName(name);
    }

    /**
     * 添加推广企业营业资质
     */
    @GetMapping("/addYyZz")
    public String addTgsZz() {
        return prefix + "/addYyZz";
    }



    /**
     * 修改推广企业密码
     */
//    @RequiresPermissions("operate:account:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/reset")
    @ResponseBody
    public AjaxResult resetPwd(Account account) {
        account.randomSalt();
        account.setPassword(passwordService.encryptPassword(
                account.getLoginName(),
                account.getPassword(),
                account.getSalt()));
        return toAjax(accountService.updateAccount(account));
    }

    /**
     * 打开推广企业授权资质弹出框
     */
    @GetMapping("/tgZzModal")
    public String tgZzModal() {
        return prefix + "/tuiguang_zz/modal";
    }

    /**
     * 添加推广企业授权资质弹出框
     */
    @GetMapping("/addTgZz")
    public String addTgZz() {
        return prefix + "/tuiguang_zz/add";
    }


    /**
     * 编辑推广企业授权资质弹出框
     */
    @GetMapping("/editTgZz")
    public String editTgZz() {
        return prefix + "/editTgZz";
    }

    /**
     * 添加推广用户姓名页面
     */
    @GetMapping("/addLinkMan")
    public String addLinkMan() {
        return prefix + "/addLinkMan";
    }

    /**
     * 添加推广用户姓名页面
     */
    @GetMapping("/editLinkMan")
    public String editLinkMan() {
        return prefix + "/editLinkMan";
    }

    /**
     * 重置密码页面
     */
    @GetMapping("/resetPwd/{accountId}")
    public String resetPwd(ModelMap modelMap, @PathVariable Integer accountId) {
        Account account = accountService.selectAccountById(accountId);
        modelMap.put("account", account);
        return prefix + "/resetPwd";
    }


    @GetMapping("/notAdminAccount")
    public String notAdminAccount() {
        return prefix + "/notAdminAccount";

    }

    /**
     * 企业账号
     *
     * @param account 参数
     */
    @PostMapping("/notAdminAccountList")
    @ResponseBody
    public TableDataInfo notAdminAccountList(Account account) {
        startPage();
        List<Account> list = accountService.selectNotAdminAccountList(account);
        return getDataTable(list);
    }

    /**
     * 账期管理时跳转到admin账号页面
     */
    @GetMapping("/adminAccount")
    @RequiresPermissions("operate:account:adminAccountView")
    public String adminAccount() {
        return "operate/accountPeriod/adminAccount";

    }

    /**
     * 查看账期时先查所有admin账号
     *
     * @param account 参数
     */
    @PostMapping("/adminAccountList")
    @ResponseBody
    public TableDataInfo adminAccountList(Account account) {
        startPage();
        List<Account> list = accountService.selectAdminAccountList(account);
        return getDataTable(list);
    }
}

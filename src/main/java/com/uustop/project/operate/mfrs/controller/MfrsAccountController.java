package com.uustop.project.operate.mfrs.controller;


import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.operate.mfrs.domain.MfrsAccount;
import com.uustop.project.operate.mfrs.service.MfrsAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 合作企业 信息操作处理
 *
 * @author ZX01
 * @date : 2019-02-15
 */
@Controller
@RequestMapping("/operate/mfrsAccount")
public class MfrsAccountController extends BaseController {

    private String prefix = "operate/mfrsAccount";

    @Autowired
    private MfrsAccountService mfrsAccountService;

    @RequiresPermissions("operate:mfrsAccount:view")
    @GetMapping()
    public String mfrs() {
        return prefix + "/mfrsAccount";
    }

    /**
     * 查询合作企业用户列表
     */
    @RequiresPermissions("operate:mfrsAccount:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MfrsAccount mfrsAccount) {
        startPage();
        List<MfrsAccount> list = mfrsAccountService.selectUserList(mfrsAccount);
        return getDataTable(list);
    }
}

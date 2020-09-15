package com.uustop.project.common.PersonUtil.controller;


import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.common.PersonUtil.service.PersonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业所有用户
 * @Auther: xh
 * @Date: 2019-02-20
 */
@Controller
@RequestMapping("/system/person")
public class PersonController extends BaseController {

    @Resource
    private PersonService personService;

    /**
     * 企业所有用户
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Person person) {
        startPage();
        List<Person> list = personService.selectPersonByEnterprise(person);
        return getDataTable(list);
    }
}


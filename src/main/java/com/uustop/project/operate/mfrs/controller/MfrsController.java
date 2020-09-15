package com.uustop.project.operate.mfrs.controller;


import com.uustop.common.constant.PictureStatusCode;
import com.uustop.common.support.Convert;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.StringUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.common.DeleteUtils.domain.TableInformation;
import com.uustop.project.common.DeleteUtils.service.DeleteService;
import com.uustop.project.operate.mfrs.domain.Mfrs;
import com.uustop.project.operate.mfrs.domain.MfrsPicture;
import com.uustop.project.operate.mfrs.service.IMfrsPictureService;
import com.uustop.project.operate.mfrs.service.IMfrsService;
import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;
import com.uustop.project.operate.mfrsLinkman.service.MfrsLinkmanService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合作企业 信息操作处理
 *
 * @Auther: xh
 * @Date: 2019-02-15
 */
@Controller
@RequestMapping("/operate/mfrs")
public class MfrsController extends BaseController {
    private String prefix = "operate/mfrs";

    @Autowired
    private IMfrsService mfrsService;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private IMfrsPictureService mfrsPictureService;

    @Autowired
    private MfrsLinkmanService mfrsLinkmanService;

    @RequiresPermissions("operate:mfrs:view")
    @GetMapping()
    public String mfrs() {
        return prefix + "/mfrs";
    }

    /**
     * 查询合作企业列表
     */
    @RequiresPermissions("operate:mfrs:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Mfrs mfrs) {
        startPage();
        List<Mfrs> list = mfrsService.selectMfrsList(mfrs);
        return getDataTable(list);
    }

    /**
     * 跳转到联系人列表
     */
    @GetMapping("/link")
    public String linkman() {
        return prefix + "/linkman";
    }

    /**
     * 查合作企业联系人列表
     */
    @PostMapping("/linkList")
    @ResponseBody
    public TableDataInfo list(MfrsLinkman linkman) {
        startPage();
        Integer mfrsId = linkman.getMfrsId();
        if (mfrsId == null) {
            linkman.setMfrsId(NumberUtils.INTEGER_ZERO);
        }
        List<MfrsLinkman> list = mfrsLinkmanService.selectLinkmanList(linkman);
        return getDataTable(list);
    }

    @PostMapping("/list/notPass")
    @ResponseBody
    public TableDataInfo listNotPass(Mfrs mfrs) {
        startPage();
        List<Mfrs> list = mfrsService.selectMfrsListNotPass(mfrs);
        return getDataTable(list);
    }

    @PostMapping("/selectById/{mfrsId}")
    @ResponseBody
    public Mfrs selectById(@PathVariable Integer mfrsId) {
        Mfrs mfrs = new Mfrs();
        mfrs.setMfrsId(mfrsId);
        Mfrs mfrs1 = mfrsService.selectById(mfrs);
        return mfrs1;
    }

    /**
     * 导出合作企业列表
     */
    @RequiresPermissions("operate:mfrs:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Mfrs mfrs) {
        List<Mfrs> list = mfrsService.selectMfrsList(mfrs);
        ExcelUtil<Mfrs> util = new ExcelUtil<Mfrs>(Mfrs.class);
        return util.exportExcel(list, "mfrs");
    }

    /**
     * 新增合作企业
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存合作企业
     */
    @RequiresPermissions("operate:mfrs:add")
    @Log(title = "合作企业", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Mfrs mfrs) {
        BaseEntityAutoUtils.autoCreateBaseEntity(mfrs);
        return toAjax(mfrsService.insertMfrs(mfrs));
    }

    /**
     * 修改合作企业
     */
    @GetMapping("/edit/{mfrsId}")
    public String edit(@PathVariable("mfrsId") Integer mfrsId, ModelMap mmap) {
        Mfrs mfrs = mfrsService.selectMfrsById(mfrsId);
        mmap.put("mfrs", mfrs);
        return prefix + "/edit";
    }

    /**
     * 修改保存合作企业
     */
    @RequiresPermissions("operate:mfrs:edit")
    @Log(title = "合作企业", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Mfrs mfrs) {
        Mfrs old = mfrsService.selectMfrsById(mfrs.getMfrsId());
        if (!StringUtils.equals(mfrs.getName(), old.getName())) {
            int count = checkName(mfrs);
            if (count > 0) {
                return AjaxResult.error("合作企业已经存在");
            }
        }
        if (!StringUtils.equals(mfrs.getShortName(), old.getShortName())) {
            int count = checkShortName(mfrs);
            if (count > 0) {
                return AjaxResult.error("简称已经存在");
            }
        }
        return toAjax(mfrsService.updateMfrs(mfrs));
    }

    /**
     * 删除合作企业
     */
    @RequiresPermissions("operate:mfrs:remove")
    @Log(title = "合作企业", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        Integer[] array = Convert.toIntArray(ids);
        TableInformation tableInfo = new TableInformation(
                "dis_mfrs", "mfrs_id", array, "mfrs_linkman");
        Integer aa = deleteService.deleteInterceptor(tableInfo);
        if (aa > 0) {
            return AjaxResult.error("基本数据已经被使用，请勿删除");
        }
        return toAjax(mfrsService.deleteMfrsByIds(ids));
    }

    /**
     * 检索生产名称
     */
    @PostMapping("/checkMfrsNameUnique")
    @ResponseBody
    public int checkName(Mfrs mfrs) {
        return mfrsService.selectCountByMfrsName(mfrs);
    }

    /**
     * 检索生产简称
     */
    @PostMapping("/checkMfrsShortNameUnique")
    @ResponseBody
    public int checkShortName(Mfrs mfrs) {
        return mfrsService.selectCountByMfrsShortName(mfrs);
    }

    /**
     * 根据推广企业查询合作企业
     */
    @PostMapping("/selectMfrsListByCompanyId")
    @ResponseBody
    public TableDataInfo selectMfrsListByCompanyId(Integer companyId) {
        List<Mfrs> list = mfrsService.selectMfrsListByCompanyId(companyId);
        return getDataTable(list);
    }

    /**
     * 公开资质图片
     *
     * @param mfrsId 企业id
     * @param map    结果
     */
    @GetMapping("/mfrsAccreditPic/{mfrsId}")
    public String mfrsAccredit(@PathVariable Integer mfrsId, ModelMap map) {
        MfrsPicture mfrsPicture = new MfrsPicture();
        mfrsPicture.setMfrsId(mfrsId);
        PictureStatusCode[] codes = PictureStatusCode.values();
        boolean[] arr = new boolean[codes.length];
        for (int i = 0; i < codes.length; i++) {
            PictureStatusCode code = codes[i];
            mfrsPicture.setType(code.value());
            List<MfrsPicture> picture = mfrsPictureService.selectPictureByMfrsIdAndType(mfrsPicture);
            arr[i] = picture.size() > 0;
        }
        Mfrs mfrs = mfrsService.selectMfrsById(mfrsId);
        map.put("isExist", arr);
        map.put("companyName", mfrs.getName());
        return prefix + "/mfrsNonEssential";
    }


    /**
     * 图片详情
     *
     * @param mfrsPicture 合作企业图片
     * @param mmap        结果
     */
    @GetMapping("/uploadPic")
    public String selectMfrsPrestorePicMap(MfrsPicture mfrsPicture, ModelMap mmap) {
        int value = PictureStatusCode.MFRS_BUSINESS_LICENSE.value();
        int value1 = PictureStatusCode.MFRS_YAOPINXUKEZHENG.value();
        Integer type = mfrsPicture.getType();
        if (type == value || type == value1) {
            mfrsPicture.setMfrsPrestoreId(NumberUtils.INTEGER_ONE);
            mmap.put("groupPic", mfrsPictureService.selectPictureByMfrsIdAndTypeOther(mfrsPicture));
        } else {
            mmap.put("groupPic", mfrsPictureService.selectPictureByMfrsIdAndType(mfrsPicture));
        }
        return "operate/common/groupPic";
    }
}

package com.uustop.project.operate.mfrs.controller;


import com.uustop.common.constant.Constants;
import com.uustop.common.constant.DelFlagConstants;
import com.uustop.common.constant.IsPassConstants;
import com.uustop.common.constant.NoticeConstants;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.operate.mfrs.domain.Mfrs;
import com.uustop.project.operate.mfrs.domain.MfrsPicture;
import com.uustop.project.operate.mfrs.domain.MfrsPrestore;
import com.uustop.project.operate.mfrs.service.IMfrsPictureService;
import com.uustop.project.operate.mfrs.service.IMfrsPrestoreService;
import com.uustop.project.operate.mfrs.service.IMfrsService;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;
import com.uustop.project.system.noticeRecord.service.INoticeRecordService;
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
@RequestMapping("/operate/mfrsPrestore")
public class MfrsPrestoreController extends BaseController {

    private String prefix = "operate/mfrs";

    @Autowired
    private IMfrsPrestoreService mfrsPrestoreService;

    @Autowired
    private INoticeRecordService noticeRecordService;

    @Autowired
    private IMfrsPictureService mfrsPictureService;

    @Autowired
    private IMfrsService mfrsService;


    @RequiresPermissions("operate:auditMfrsPrestore:view")
    @GetMapping("/auditMfrsPrestore")
    public String auditMfrsPrestore() {
        return prefix + "/auditMfrsPrestore";
    }

    /**
     * 查询待审核合作企业
     */
    @PostMapping("/selectAuditMfrsPrestoreList")
    @ResponseBody
    public TableDataInfo selectAuditMfrsPrestoreList(MfrsPrestore mfrsPrestore) {
        startPage();
        if (mfrsPrestore.getDelFlag() != null) {
            if (mfrsPrestore.getDelFlag().equals(DelFlagConstants.DAITIJIAO)) {
                mfrsPrestore.setDelFlag(DelFlagConstants.DAISHENHE);
                mfrsPrestore.setMfrsId(Integer.valueOf(IsPassConstants.CHECK_PASS));
            } else if (mfrsPrestore.getDelFlag().equals(DelFlagConstants.DAISHENHE)) {
                mfrsPrestore.setMfrsId(Integer.valueOf(IsPassConstants.CHECK_NOT_PASS));
            }
        }
        List<MfrsPrestore> list = mfrsPrestoreService.selectAuditMfrsPrestoreList(mfrsPrestore);
        return getDataTable(list);
    }

    /**
     * 通过申请
     */
    @PostMapping("/applyMfrsPrestore")
    @ResponseBody
    public AjaxResult applyMfrsPrestore(Integer mfrsId, Integer mfrsPrestoreId) {
        Mfrs mfrs = mfrsService.selectMfrsById(mfrsId);
        if (mfrs != null && mfrs.getIsPass().equals(Constants.SUCCESS)) {
            return AjaxResult.error("该合作企业已经审核通过，请勿重复审核");
        }
        return toAjax(mfrsPrestoreService.applyMfrsPrestore(mfrsId, mfrsPrestoreId));
    }

    /**
     * 更新合作企业信息审核
     */
    @PostMapping("/review/again")
    @ResponseBody
    public AjaxResult applyReviewAgain(String ids) {
        return toAjax(mfrsPrestoreService.applyReviewAgain(ids));
    }

    @GetMapping("/bindingMfrs")
    public String bindingMfrs() {
        return prefix + "/bindingMfrs";
    }

    /**
     * 驳回申请
     */
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(Integer mfrsPrestoreId, NoticeRecord noticeRecord) {
        //发送消息
        Person person = new Person();
        MfrsPrestore mfrsPrestore = mfrsPrestoreService.selectMfrsPrestoreById(mfrsPrestoreId);
        mfrsPrestore.setRemark(noticeRecord.getNoticeContent());
        noticeRecord.setNoticeContent("基本信息被驳回，驳回信息为：" + noticeRecord.getNoticeContent());
        BaseEntityAutoUtils.autoUpdateBaseEntity(mfrsPrestore);
        mfrsPrestoreService.updateMfrsById(mfrsPrestore);
        String createBy = mfrsPrestore.getCreateBy();
        person.setLoginName(createBy);
        person.setType(NoticeConstants.MFRS);
        noticeRecordService.sendNoticeRecord(person, noticeRecord);
        return toAjax(mfrsPrestoreService.reject(mfrsPrestoreId));
    }

    /**
     * 资质产查看
     */
    @GetMapping("/selectReviewMap")
    public String selectReviewAgainMap(MfrsPicture mfrsPicture, ModelMap mmap) {
        mmap.put("groupPic", mfrsPictureService.selectPictureByMfrsIdAndTypeOther(mfrsPicture));
        return "operate/common/groupPic";
    }

    /**
     * 驳回信息
     */
    @GetMapping("/editBz/{id}")
    public String editBz(@PathVariable("id") Integer id, ModelMap mmap) {
        MfrsPrestore company = mfrsPrestoreService.selectMfrsPrestoreById(id);
        if (!company.getDelFlag().equals(DelFlagConstants.WEITONGGUO)) {
            company.setRemark(null);
        }
        mmap.put("remark", company.getRemark());
        mmap.put("companyId", id);
        mmap.put("companyName", company.getName());
        return prefix + "/editBz";
    }

}

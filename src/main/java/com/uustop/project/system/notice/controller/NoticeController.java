package com.uustop.project.system.notice.controller;

import com.uustop.common.support.Convert;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.poi.ExcelUtil;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.system.notice.domain.Notice;
import com.uustop.project.system.notice.service.INoticeService;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知公告 信息操作处理
 *
 * @author xh
 * @Date: 2019-04-19
 */
@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {

    private String prefix = "system/notice";

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice() {
        return prefix + "/notice";
    }

    @GetMapping("/noticeRecord/{noticeId}")
    public String noticeRecord(@PathVariable("noticeId") Integer noticeId, ModelMap map) {
        map.put("noticeId", noticeId);
        return prefix + "/noticeRecord";
    }

    /**
     * 查询通知公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Notice notice) {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 导出通知公告列表
     */
    @RequiresPermissions("system:notice:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Notice notice) {
        List<Notice> list = noticeService.selectNoticeList(notice);
        ExcelUtil<Notice> util = new ExcelUtil<Notice>(Notice.class);
        return util.exportExcel(list, "notice");
    }


    /**
     * 新增通知公告
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存通知公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Notice notice) {
        notice.setSendType(2);
        BaseEntityAutoUtils.autoBaseEntity(notice);
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Integer noticeId, ModelMap mmap) {
        Notice notice = noticeService.selectNoticeById(noticeId);
        mmap.put("notice", notice);
        return prefix + "/edit";
    }

    /**
     * 修改保存通知公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Notice notice) {
        if (notice.getSendStatus() == 1) {
            return AjaxResult.error("此消息已经发送完成，请勿修改");
        }
        BaseEntityAutoUtils.autoUpdateBaseEntity(notice);
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        String[] array = Convert.toStrArray(ids);
        int code = 0;
        for (String s : array) {
            Notice notice = noticeService.selectNoticeById(Integer.valueOf(s));
            if (notice.getSendStatus() == 1) {
                code++;
            }
        }
        if (code > 0) {
            return AjaxResult.error("有消息已经发送请勿删除");
        }
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }


    @GetMapping("/read/{noticeId}")
    public String read(@PathVariable("noticeId")Integer noticeId,ModelMap mmap) {
        Notice notice = noticeService.selectNoticeById(noticeId);
        mmap.put("notice", notice);
        return prefix + "/read";
    }

    @PostMapping("/read")
    @ResponseBody
    public AjaxResult resdNotice(Notice notice) {
        if (notice.getSendStatus() == 1) {
            return AjaxResult.error("此消息已经发送完成，请勿修改");
        }
        BaseEntityAutoUtils.autoUpdateBaseEntity(notice);
        return toAjax(noticeService.updateNotice(notice));
    }

}

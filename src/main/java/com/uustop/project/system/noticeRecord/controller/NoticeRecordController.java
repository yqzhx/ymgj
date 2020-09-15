package com.uustop.project.system.noticeRecord.controller;


import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.bean.BeanUtils;
import com.uustop.framework.aspectj.lang.annotation.Log;
import com.uustop.framework.aspectj.lang.enums.BusinessType;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.framework.web.page.TableDataInfo;
import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.common.PersonUtil.service.PersonService;
import com.uustop.project.system.notice.domain.Notice;
import com.uustop.project.system.notice.service.INoticeService;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;
import com.uustop.project.system.noticeRecord.service.INoticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 通知公告 信息操作处理
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-04-19
 */
@Controller
@RequestMapping("/system/noticeRecord")
public class NoticeRecordController extends BaseController {

    private String prefix = "system/noticeRecord";

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private INoticeRecordService noticeRecordService;

    @Autowired
    private PersonService personService;


    /**
     * 查询通知公告列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoticeRecord noticeRecord) {
        startPage();
        List<NoticeRecord> list = noticeRecordService.selectNoticeRecordList(noticeRecord);
        return getDataTable(list);
    }

    /**
     * 审核通过
     */
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/send")
    @ResponseBody
    public AjaxResult send(Integer noticeId) {
        Notice notice = noticeService.selectNoticeById(noticeId);
        //判断是否已读
        Integer sendStatus = notice.getSendStatus();
        if (sendStatus == 1) {
            return AjaxResult.error("您已经发送过了");
        }
        //发送人群筛选
        Integer type = notice.getEnterpriseType();
        Integer enterpriseId = notice.getEnterpriseId();
        Integer sendTo = notice.getSendTo();
        Person person = new Person();
        if (type != -1) {
            //判断是否是所有人还是单人
            person.setType(type);
            if (enterpriseId != -1) {
                person.setEnterpriseId(enterpriseId);
                if (sendTo != -1) {
                    person.setId(sendTo);
                }
            }
        }
        //查询发送人群并发送
        List<Person> people = personService.selectPersonByEnterprise(person);
        for (Person item : people) {
            NoticeRecord noticeRecord = new NoticeRecord();
            BeanUtils.copyBeanProp(noticeRecord, notice);
            noticeRecord.setEnterpriseId(item.getEnterpriseId());
            noticeRecord.setEnterpriseType(item.getType());
            noticeRecord.setSendTo(item.getId());
            BaseEntityAutoUtils.autoBaseEntity(noticeRecord);
            noticeRecordService.insertNoticeRecord(noticeRecord);
        }
        //修改消息状态
        notice.setSendStatus(1);
        return toAjax(noticeService.updateNotice(notice));
    }


    /**
     * 已读通知公告
     */
    @PostMapping("/read")
    @ResponseBody
    public AjaxResult read(Integer noticeRecordId) {
        NoticeRecord noticeRecord = noticeRecordService.selectNoticeRecordById(noticeRecordId);
        noticeRecord.setReadStatus(1);
        return toAjax(noticeRecordService.updateNoticeRecord(noticeRecord));
    }
}

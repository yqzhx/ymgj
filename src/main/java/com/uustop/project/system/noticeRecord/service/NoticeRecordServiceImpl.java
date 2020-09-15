package com.uustop.project.system.noticeRecord.service;


import com.uustop.common.constant.NoticeTypeConstants;
import com.uustop.common.support.Convert;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.common.PersonUtil.mapper.PersonMapper;
import com.uustop.project.monitor.online.domain.UserOnline;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;
import com.uustop.project.system.noticeRecord.mapper.NoticeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知公告 服务层实现
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-04-19
 */
@Service
public class NoticeRecordServiceImpl implements INoticeRecordService {

    @Autowired
    private NoticeRecordMapper noticeRecordMapper;
    @Autowired
    private PersonMapper personMapper;

    /**
     * 查询通知公告信息
     *
     * @param noticeRecordId 通知公告ID
     * @return 通知公告信息
     */
    @Override
    public NoticeRecord selectNoticeRecordById(Integer noticeRecordId) {
        return noticeRecordMapper.selectNoticeRecordById(noticeRecordId);
    }

    /**
     * 查询通知公告列表
     *
     * @param noticeRecord 通知公告信息
     * @return 通知公告集合
     */
    @Override
    public List<NoticeRecord> selectNoticeRecordList(NoticeRecord noticeRecord) {
        return noticeRecordMapper.selectNoticeRecordList(noticeRecord);
    }

    /**
     * 新增通知公告
     *
     * @param noticeRecord 通知公告信息
     * @return 结果
     */
    @Override
    public int insertNoticeRecord(NoticeRecord noticeRecord) {
        return noticeRecordMapper.insertNoticeRecord(noticeRecord);
    }

    /**
     * 修改通知公告
     *
     * @param noticeRecord 通知公告信息
     * @return 结果
     */
    @Override
    public int updateNoticeRecord(NoticeRecord noticeRecord) {
        return noticeRecordMapper.updateNoticeRecord(noticeRecord);
    }

    /**
     * 删除通知公告对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeRecordByIds(String ids) {
        return noticeRecordMapper.deleteNoticeRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 批量添加通知公告
     *
     * @param records 集合
     * @return 结果
     */
    @Override
    public int batchInsertNoticeRecord(List<NoticeRecord> records) {
        return noticeRecordMapper.batchInsertNoticeRecord(records);
    }

    @Override
    public List<UserOnline> selectOnlinePerson(UserOnline userOnline) {
        return noticeRecordMapper.selectOnlinePerson(userOnline);
    }

    @Override
    public void sendNoticeRecord(Person person, NoticeRecord noticeRecord) {
        List<Person> people = personMapper.selectPersonByEnterprise(person);
        for (Person per : people) {
            NoticeRecord record = new NoticeRecord();
            record.setNoticeTitle("系统通知");
            record.setNoticeContent(noticeRecord.getNoticeContent());
            record.setNoticeType(NoticeTypeConstants.REGISTER);
            record.setEnterpriseId(per.getEnterpriseId());
            record.setEnterpriseType(per.getType());
            record.setSendTo(per.getId());
            record.setSendType(1);
            BaseEntityAutoUtils.autoBaseEntity(record);
            noticeRecordMapper.insertNoticeRecord(record);
        }
    }
}

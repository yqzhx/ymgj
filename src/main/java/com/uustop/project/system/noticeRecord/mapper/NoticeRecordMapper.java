package com.uustop.project.system.noticeRecord.mapper;


import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.monitor.online.domain.UserOnline;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;

import java.util.List;

/**
 * 通知公告 数据层
 *
 * @author tianhui.yu@hqq.vip
 * @Date: 2019-04-19
 */
public interface NoticeRecordMapper {
    /**
     * 查询通知公告信息
     *
     * @param noticeRecordId 通知公告ID
     * @return 通知公告信息
     */
    public NoticeRecord selectNoticeRecordById(Integer noticeRecordId);

    /**
     * 查询通知公告列表
     *
     * @param noticeRecord 通知公告信息
     * @return 通知公告集合
     */
    public List<NoticeRecord> selectNoticeRecordList(NoticeRecord noticeRecord);

    /**
     * 新增通知公告
     *
     * @param noticeRecord 通知公告信息
     * @return 结果
     */
    public int insertNoticeRecord(NoticeRecord noticeRecord);

    /**
     * 修改通知公告
     *
     * @param noticeRecord 通知公告信息
     * @return 结果
     */
    public int updateNoticeRecord(NoticeRecord noticeRecord);

    /**
     * 删除通知公告
     *
     * @param noticeRecordId 通知公告ID
     * @return 结果
     */
    public int deleteNoticeRecordById(Integer noticeRecordId);

    /**
     * 批量删除通知公告
     *
     * @param noticeRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeRecordByIds(String[] noticeRecordIds);


    /**
     * 批量添加通知公告
     *
     * @param records 集合
     * @return 结果
     */
    int batchInsertNoticeRecord(List<NoticeRecord> records);


    /**
     * 查询所有在线用户
     *
     * @param userOnline 在线用户信息
     */
    List<UserOnline> selectOnlinePerson(UserOnline userOnline);

}
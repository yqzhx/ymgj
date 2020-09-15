package com.uustop.project.operate.mfrs.service;


import com.uustop.common.constant.DelFlagConstants;
import com.uustop.common.constant.IsPassConstants;
import com.uustop.common.constant.NoticeConstants;
import com.uustop.common.constant.UserConstants;
import com.uustop.common.support.Convert;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.RedisUtils;
import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.operate.mfrs.domain.Mfrs;
import com.uustop.project.operate.mfrs.domain.MfrsAccount;
import com.uustop.project.operate.mfrs.domain.MfrsDept;
import com.uustop.project.operate.mfrs.domain.MfrsPrestore;
import com.uustop.project.operate.mfrs.mapper.*;
import com.uustop.project.operate.mfrsLinkman.domain.MfrsLinkman;
import com.uustop.project.operate.mfrsLinkman.mapper.MfrsLinkmanMapper;
import com.uustop.project.operate.picture.domain.Picture;
import com.uustop.project.operate.picture.mapper.PictureMapper;
import com.uustop.project.system.noticeRecord.domain.NoticeRecord;
import com.uustop.project.system.noticeRecord.service.INoticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作企业 服务层实现
 */
@Service("mfrsPrestore")
public class MfrsPrestoreServiceImpl implements IMfrsPrestoreService {

    @Autowired
    private MfrsPrestoreMapper mfrsPrestoreMapper;
    @Autowired
    private MfrsUserRoleMapper mfrsUserRoleMapper;
    @Autowired
    private MfrsMapper mfrsMapper;
    @Autowired
    private MfrsAccountMapper mfrsAccountMapper;
    @Autowired
    private MfrsDeptMapper mfrsDeptMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private MfrsLinkmanMapper mfrsLinkmanMapper;
    @Autowired
    private INoticeRecordService noticeRecordService;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public List<MfrsPrestore> selectAuditMfrsPrestoreList(MfrsPrestore mfrsPrestore) {
        return mfrsPrestoreMapper.selectAuditMfrsPrestoreList(mfrsPrestore);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int applyMfrsPrestore(Integer mfrsId, Integer mfrsPrestoreId) {

        MfrsPrestore mfrsPrestore = mfrsPrestoreMapper.selectMfrsPrestoreById(mfrsPrestoreId);


        //修改原合作企业
        Mfrs mfrs = mfrsMapper.selectMfrsById(mfrsId);
        mfrs.setLinkmanName(mfrsPrestore.getLinkmanName());
        mfrs.setPhoneNumber(mfrsPrestore.getPhoneNumber());
        mfrs.setIsPass(IsPassConstants.CHECK_PASS);
        mfrs.setDelFlag(DelFlagConstants.YITONGGUO);
        //BaseEntityAutoUtils.autoBaseEntity(mfrs);
        mfrs.setUpdateBy(mfrsPrestore.getUpdateBy());
        mfrs.setUpdateTime(mfrsPrestore.getUpdateTime());
        mfrs.setConfirmTime(new Date());
        mfrsMapper.updateMfrs(mfrs);

        //修改临时表
        mfrsPrestoreMapper.updateMfrsId(mfrsId, mfrsPrestoreId);
        MfrsAccount user = mfrsAccountMapper.selectUserByLoginName(mfrsPrestore.getCreateBy());
        // 删除用户与角色关联
        mfrsUserRoleMapper.deleteUserRoleByAccountId(user.getAccountId());
        // 新增用户与角色管理
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", user.getAccountId());
        map.put("roleId", 2);
        mfrsUserRoleMapper.batchUserRole(map);
        //重置redis权限
        List<String> lrange = redisUtils.lrange(user.getAccountId() + user.getLoginName() + user.getPhonenumber(), 0, -1);
        for(String sessionId : lrange){
            redisUtils.del(sessionId);
        }
        redisUtils.del(user.getAccountId() + user.getLoginName() + user.getPhonenumber());
        redisUtils.del("shiro:cache:com.uustop.framework.shiro.realm.UserRealm.authorizationCache:"+user.getLoginName());
        //添加默认部门
        MfrsDept dept = new MfrsDept();
        dept.setMfrsId(mfrsId.toString());
        dept.setParentId(0L);
        dept.setDeptName(mfrs.getName());
        dept.setOrderNum("1");
        mfrsDeptMapper.insertDept(dept);

        //更新用户所属企业
        MfrsAccount mfrsAccount = new MfrsAccount();
        mfrsAccount.setAccountId(user.getAccountId());
        mfrsAccount.setMfrsId(mfrsId.longValue());
        mfrsAccount.setDeptId(dept.getDeptId());
        mfrsAccount.setUserName(mfrsPrestore.getUserName());
        mfrsAccountMapper.updateMfrsAccount(mfrsAccount);

        //添加联系人
        MfrsLinkman mfrsLinkman = new MfrsLinkman();
        mfrsLinkman.setMfrsId(mfrsId);
        mfrsLinkman.setMobile(mfrs.getPhoneNumber());
        mfrsLinkman.setLinkName(mfrsPrestore.getUserName());
        BaseEntityAutoUtils.autoBaseEntity(mfrsLinkman);
        mfrsLinkmanMapper.insertLinkman(mfrsLinkman);

        //将资质图片绑定到合作企业下
        map.put("mfrsId", mfrsId);
        map.put("mfrsPrestoreId", mfrsPrestoreId);
        mfrsPrestoreMapper.bindingPicToMfrs(map);

        //发送消息
        Person person = new Person();
        person.setEnterpriseId(mfrsId);
        person.setType(NoticeConstants.MFRS);
        person.setUserType(Integer.valueOf(UserConstants.USER_TYPE_ADMIN));
        NoticeRecord noticeRecord = new NoticeRecord();
        noticeRecord.setNoticeContent("恭喜注册成功！欢迎使用XXX！");
        noticeRecordService.sendNoticeRecord(person, noticeRecord);

        return mfrsPrestoreMapper.applyMfrsPrestore(mfrsPrestoreId);
    }


    @Override
    public int reject(Integer mfrsId) {
        return mfrsPrestoreMapper.reject(mfrsId);
    }

    @Override
    public List<String> selectMfrsPrestorePic(String mfrsPrestoreId, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("mfrsPrestoreId", mfrsPrestoreId);
        map.put("type", type);
        return mfrsPrestoreMapper.selectMfrsPrestorePic(map);
    }

    @Override
    public List<MfrsPrestore> selectReviewAgainList(MfrsPrestore mfrsPrestore) {
        return mfrsPrestoreMapper.selectReviewAgainList(mfrsPrestore);
    }

    @Override
    public int applyReviewAgain(String ids) {
        String[] strIds = Convert.toStrArray(ids);
        int rowCount = 0;
        for (String mfrsPrestoreId : strIds) {
            MfrsPrestore mfrsPrestore = mfrsPrestoreMapper.selectMfrsPrestoreById(Integer.parseInt(mfrsPrestoreId));
            //修改原合作企业
            Mfrs mfrs = mfrsMapper.selectMfrsById(mfrsPrestore.getMfrsId());
            mfrs.setLinkmanName(mfrsPrestore.getUserName());
            mfrs.setPhoneNumber(mfrsPrestore.getPhoneNumber());
            mfrs.setIsPass(IsPassConstants.CHECK_PASS);
            mfrs.setDelFlag(DelFlagConstants.YITONGGUO);
            mfrs.setConfirmTime(new Date());
            //BaseEntityAutoUtils.autoBaseEntity(mfrs);
            mfrs.setUpdateBy(mfrsPrestore.getUpdateBy());
            mfrs.setUpdateTime(mfrsPrestore.getUpdateTime());
            mfrsMapper.updateMfrs(mfrs);
            //MfrsAccount user = mfrsAccountMapper.selectUserByLoginName(mfrsPrestore.getCreateBy());
            //MfrsAccount mfrsAccount = new MfrsAccount();
            //mfrsAccount.setAccountId(user.getAccountId());
            //mfrsAccount.setMfrsId(user.getMfrsId().longValue());
            //mfrsAccount.setDeptId(user.getDeptId());
            //mfrsAccount.setUserName(mfrsPrestore.getUserName());
            //mfrsAccountMapper.updateMfrsAccount(mfrsAccount);

            Map<String, Object> map = new HashMap<>();
            //将资质图片绑定到合作企业下
            map.put("mfrsId", mfrsPrestore.getMfrsId());
            map.put("mfrsPrestoreId", mfrsPrestoreId);
            mfrsPrestoreMapper.bindingPicToMfrs(map);
            rowCount += mfrsPrestoreMapper.applyMfrsPrestore(Integer.parseInt(mfrsPrestoreId));
        }
        return rowCount;
    }

    @Override
    public List<Picture> selectReviewAgainMap(MfrsPrestore mfrsPrestore) {
        return pictureMapper.selectReviewPic(mfrsPrestore);
    }

    @Override
    public List<Picture> selectReviewAgainMfrsPrestore(MfrsPrestore mfrsPrestore) {
        return pictureMapper.selectReviewPicPrestore(mfrsPrestore);
    }

    @Override
    public MfrsPrestore selectMfrsPrestoreById(Integer valueOf) {
        return mfrsPrestoreMapper.selectMfrsPrestoreById(valueOf);
    }
    @Override
    public void updateMfrsById(MfrsPrestore mfrsPrestore){
        mfrsPrestoreMapper.updateMfrsById(mfrsPrestore);
    }
}

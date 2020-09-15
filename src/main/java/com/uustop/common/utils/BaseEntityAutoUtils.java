package com.uustop.common.utils;

import com.uustop.common.utils.security.ShiroUtils;
import com.uustop.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 基类赋值工具类
 */
public class BaseEntityAutoUtils {

    public static Object autoBaseEntity(BaseEntity baseEntity){
        Date date = new Date();
        baseEntity.setCreateTime(date);
        baseEntity.setUpdateTime(date);
        baseEntity.setCreateBy(ShiroUtils.getSysUser().getLoginName());
        baseEntity.setUpdateBy(ShiroUtils.getSysUser().getLoginName());
        return baseEntity;
    }

    public static Object autoUpdateBaseEntity(BaseEntity baseEntity){
        Date date = new Date();
        baseEntity.setUpdateTime(date);
        baseEntity.setUpdateBy(ShiroUtils.getSysUser().getLoginName());
        return baseEntity;
    }

    public static Object autoCreateBaseEntity(BaseEntity baseEntity){
        Date date = new Date();
        baseEntity.setCreateTime(date);
        baseEntity.setCreateBy(ShiroUtils.getSysUser().getLoginName());
        return baseEntity;
    }
}

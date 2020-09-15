package com.uustop.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.uustop.common.constant.Constants;
import com.uustop.common.exception.user.UserPasswordNotMatchException;
import com.uustop.common.exception.user.UserPasswordRetryLimitExceedException;
import com.uustop.common.utils.MessageUtils;
import com.uustop.framework.manager.AsyncManager;
import com.uustop.framework.manager.factory.AsyncFactory;
import com.uustop.project.system.user.domain.User;

/**
 * 登录密码方法
 * 
 * @author uustop
 */
@Component
public class PasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(User user, String password)
    {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if(Integer.valueOf(maxRetryCount).intValue() != 0) {
            if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
                throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
            }
        }
        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(User user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    public static void main(String[] args)
    {
        System.out.println(new PasswordService().encryptPassword("河西疾控1", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("河西区天塔街社区卫生服务中心", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("河西区疾控", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("北辰sun", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("津南区疾控", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("红桥zhou", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("武清An", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("南营门社区", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("西堤头镇社区", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("和平区疾控操作人", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("五中心医院", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("河东区疾控", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("咸水沽", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("津南区疾控操作人", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("红桥区疾控操作人", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("天津市东丽区疾病预防控制中心", "admin123", "111111"));
    }
}

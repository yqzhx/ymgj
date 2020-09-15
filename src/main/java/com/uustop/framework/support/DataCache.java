package com.uustop.framework.support;

import com.uustop.project.system.user.domain.User;
import com.uustop.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userNameUtil")
public class DataCache {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获得用户集合(user表)
     *
     * @return 结果
     */
    public List<User> getUserList() {
        return userMapper.selectUserNameByLoginName();
    }

    /**
     * 获得用户类型获得所有用户(enterprise表)
     *
     * @return 结果
     */
    public List<User> getUserList(Integer type) {
        return userMapper.selectUserNameByLoginNameByType(type);
    }
}
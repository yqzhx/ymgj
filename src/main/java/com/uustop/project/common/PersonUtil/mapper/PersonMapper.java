package com.uustop.project.common.PersonUtil.mapper;


import com.uustop.project.common.PersonUtil.domain.Person;

import java.util.List;

/**
 * 查询所有用户信息{推广商，合作企业，疾控中心}
 *
 * @author xh
 */
public interface PersonMapper {

    /**
     * 根据不同的表查询所有用户
     *
     * @param person 查询所有企业用户
     * @return 所有用户
     */
    List<Person> selectPersonByEnterprise(Person person);

    /**
     * 查询在用户信息根据id
     */
    Person selectPersonById(Person person);

}

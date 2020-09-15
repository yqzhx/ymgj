package com.uustop.project.common.PersonUtil.service;


import com.uustop.project.common.PersonUtil.domain.Person;
import com.uustop.project.common.PersonUtil.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 所有用户
 *
 * @author xh
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;


    /**
     * 根据不同的表查询所有用户
     *
     * @param person 查询所有企业用户
     * @return 所有用户
     */
    @Override
    public List<Person> selectPersonByEnterprise(Person person) {
        return personMapper.selectPersonByEnterprise(person);
    }

    @Override
    public Person selectPersonById(Person person) {
        return personMapper.selectPersonById(person);
    }

}

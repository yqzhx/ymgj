package com.uustop.project.system.main.service;


import com.uustop.project.system.main.domain.Main;
import com.uustop.project.system.main.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMainServiceImp implements IMainService{

    @Autowired
    MainMapper mainMapper;


    @Override
    public List<Main> selectContractCount(){
        return mainMapper.selectContractCount();
    }

    @Override
    public List<Main> selectMfrsContractCount(){
        return mainMapper.selectMfrsContractCount();
    }

    @Override
    public List<Main> selectCdcContractCount(){
        return mainMapper.selectCdcContractCount();
    }
    @Override
    public List<Main> selectCompanyContractCount(){
        return mainMapper.selectCompanyContractCount();
    }

    @Override
    public Integer selectDaiFaHuoContract(){
        return mainMapper.selectDaiFaHuoContract();
    }

    @Override
    public Integer selectWeiHuiKuanContract(){
        return mainMapper.selectWeiHuiKuanContract();
    }

    @Override
    public Integer selectTodayContract(){
        return mainMapper.selectTodayContract();
    }
    @Override
    public Integer selectHistoryContract(){
        return mainMapper.selectHistoryContract();
    }

    @Override
    public List<Integer> selectAllAccount(){
        return mainMapper.selectAllAccount();
    }

}

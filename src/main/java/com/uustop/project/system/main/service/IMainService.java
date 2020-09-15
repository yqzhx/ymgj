package com.uustop.project.system.main.service;


import com.uustop.project.system.main.domain.Main;

import java.util.List;

public interface IMainService {

    public List<Main> selectContractCount();

    public List<Main> selectMfrsContractCount();
    public List<Main> selectCdcContractCount();
    public List<Main> selectCompanyContractCount();

    public Integer selectDaiFaHuoContract();
    public Integer selectWeiHuiKuanContract();

    public Integer selectTodayContract();

    public Integer selectHistoryContract();
    public List<Integer> selectAllAccount();

}

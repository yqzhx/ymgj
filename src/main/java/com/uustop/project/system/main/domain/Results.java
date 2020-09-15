package com.uustop.project.system.main.domain;

import java.util.List;

public class Results {

    private List mfrs;
    private List cdc;
    private List company;

    private Integer todayContract;
    private Integer historyContract;
    private Integer daiFaHuo;
    private Integer weiHuiKuan;

    public Integer getTodayContract() {
        return todayContract;
    }

    public void setTodayContract(Integer todayContract) {
        this.todayContract = todayContract;
    }

    public Integer getHistoryContract() {
        return historyContract;
    }

    public void setHistoryContract(Integer historyContract) {
        this.historyContract = historyContract;
    }

    public Integer getDaiFaHuo() {
        return daiFaHuo;
    }

    public void setDaiFaHuo(Integer daiFaHuo) {
        this.daiFaHuo = daiFaHuo;
    }

    public Integer getWeiHuiKuan() {
        return weiHuiKuan;
    }

    public void setWeiHuiKuan(Integer weiHuiKuan) {
        this.weiHuiKuan = weiHuiKuan;
    }

    public List getMfrs() {
        return mfrs;
    }

    public void setMfrs(List mfrs) {
        this.mfrs = mfrs;
    }

    public List getCdc() {
        return cdc;
    }

    public void setCdc(List cdc) {
        this.cdc = cdc;
    }

    public List getCompany() {
        return company;
    }

    public void setCompany(List company) {
        this.company = company;
    }
}

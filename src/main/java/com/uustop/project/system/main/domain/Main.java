package com.uustop.project.system.main.domain;

public class Main {
    private Integer mfrsId;
    private String mfrsName;
    private Integer countContract;
    private Integer totalAmount;
    private String Month;
    private String cdcName;
    private Integer cdcId;
    private String companyName;
    private Integer companyId;

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public Integer getCountContract() {
        return countContract;
    }

    public void setCountContract(Integer countContract) {
        this.countContract = countContract;
    }

    public Integer getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(Integer mfrsId) {
        this.mfrsId = mfrsId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getCdcName() {
        return cdcName;
    }

    public void setCdcName(String cdcName) {
        this.cdcName = cdcName;
    }

    public Integer getCdcId() {
        return cdcId;
    }

    public void setCdcId(Integer cdcId) {
        this.cdcId = cdcId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}

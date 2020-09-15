package com.uustop.project.common;

public interface BusinessConstant {
    /**
     * 订单未提交state
     */
    public static final Integer UNSUBMIT = 0;
    /**
     * 订单已提交未分拣state
     */
    public static final Integer SUBMITED = 1;
    /**
     * 订单已分拣state
     */
    public static final Integer SORTED = 2;
    /**
     * 订单被驳回state
     */
    public static final Integer DUSNUSSED = 3;

    /**
     * 合同状态待发货state
     */
    public static final Integer TOBESHIPPED = 0;
    /**
     * 合同状态已发货state
     */
    public static final Integer SHIPPED = 1;

    /**
     * 合同状态未完成complete_state
     */
    public static final Integer COMPLETESTATEING = 0;
    /**
     * 合同状态已完成complete_state
     */
    public static final Integer COMPLETESTATEED = 1;

    /**
     * 合同统计状态待发货statistics_state
     */
    public static final Integer DAIFAHUO = 0;
    /**
     * 合同统计状态部分发货statistics_state
     */
    public static final Integer BUFENFAHUO = 1;
    /**
     * 合同统计状态已发货statistics_state
     */
    public static final Integer YIFAHUO = 2;
    /**
     * 合同统计状态部分回款statistics_state
     */
    public static final Integer BUFENHUIKUAN = 3;
    /**
     * 合同统计状态已完成statistics_state
     */
    public static final Integer YIWANCHENG = 4;

    /**
     * 合同状态未收货receive_state
     */
    public static final Integer UNRECEIVED = 0;
    /**
     * 合同状态已收货receive_state
     */
    public static final Integer RECEIVED = 1;

    /**
     * 合同详情状态待发货state
     */
    public static final Integer CONTRACTDETAILSTOBESHIPPED = 0;
    /**
     * 合同详情状态已发货state
     */
    public static final Integer CONTRACTDETAILSSHIPPED = 1;
    /**
     * 合同详情状态未完成complete_state
     */
    public static final Integer CONTRACTDETAILSCOMPLETESTATEING = 0;
    /**
     * 合同详情状态已完成complete_state
     */
    public static final Integer CONTRACTDETAILSCOMPLETESTATEED = 1;


    /**
     * 账期内回款
     */
    public static final Integer ZHANGQINEIHUIKUAN = 0;
    /**
     * 账期外回款
     */
    public static final Integer ZHANGQIWAIHUIKUAN = 1;
    /**无账期*/
    public static final Integer WUZHANGQI = 2;

    public static final Integer TUIGUANGQIYERENWUDAIQUEREN = 0;
    public static final Integer TUIGUANGQIYERENWUYIQUEREN = 1;
    public static final Integer TUIGUANGQIYERENWUWEIQUEREN = 2;

    /**外勤任务待确认*/
    public static final Integer WAIQINRENWUDAIQUEREN = 0;
    /**外勤任务已确认*/
    public static final Integer WAIQINRENWUYIQUEREN = 1;
    /**外勤任务未确认*/
    public static final Integer WAIQINRENWUWEIQUEREN = 2;
}

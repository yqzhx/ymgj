package com.uustop.common.constant;

import com.uustop.common.utils.file.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PictureStatusCode {
    /**
     * 推广企业头像
     */
    PROM_HEAD_PORTRAIT(0, "推广企业头像"),
    /**
     * 推广企业营业执照
     */
    PROM_BUSINESS_LICENSE(1, "推广企业营业执照"),
    /**
     * 推广授权资质
     */
    PROM_APTITUDE(2, "推广授权资质"),
    /**
     * 负责人授权书
     */
    PROM_FUZERENSHOUQUANSHU(3, "负责人授权书"),
    PROM_CONTRACT_PIC(4, "合同照片"),
    PROM_CONTRACT_RECEIPT(5, "合同发票"),
    MFRS_BUSINESS_LICENSE(6, "合作企业营业执照"),
    MFRS_YAOPINXUKEZHENG(7, "合作企业药品生产许可证"),
    MFRS_GMP(8, "合作企业GMP证书"),
    MFRS_ZHUCEPIJIAN(9, "合作企业注册批件"),
    MFRS_ZHILIANGBIAOZHUN(10, "合作企业质量标准"),
    MFRS_WUJIAPINWEN(11, "合作企业物价批文"),
    MFRS_FAPIAOYANGBEN(12, "合作企业增值税发票样本"),
    MFRS_KAIPIAOXINXI(13, "合作企业开票信息"),
    NFRS_KAIHUXUKEZHENG(14, "合作企业开户许可证"),
    MFRS_SUIHUOTONGXINGDAN(15, "合作企业随货同行单"),
    MFRS_zhiliangtixidiaochabiao(16, "合作企业质量体系调查表"),
    MFRS_ZHILIANGBAOZHENGXIEYISHU(17, "合作企业质量保证协议书"),
    MFRS_YINZHANGYINMO(18, "合作企业印章印模"),
    MFRS_QIYESHUANGMINGSHU(19, "合作企业说明书"),
    MFRS_BAOZHUANGJIBIAOQIAN(20, "合作企业包装及标签"),
    MFRS_FARENSHOUQUANSHU(21, "合作企业法人授权书"),


    PROM_MEETING_EXHIBITION_PICTURE(30, "会展图片"),
    PROM_MEETING_EXHIBITION_DOCUMENT(31, "会议附件文件"),
    PROM_MEETING_EXHIBITION_VISIT(32, "会展拜访文件"),
    PROM_MEETING_EXHIBITION_INVOICE(33, "会展发票文件"),

    PROM_VISIT_PIC(34, "拜访照片"),

    SYSTEM_MENU_PIC(40,"菜单目录图片"),

    BATCH_PIC(41,"批签发图片"),

    INSPECTION_REPORT(42,"质检报告");


    private final int value;

    private final String reasonPhrase;

    PictureStatusCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String reasonPhrase() {
        return this.reasonPhrase;
    }

    /**
     * 根据value返回驼峰文件名
     */
    public static String getTypeByVallue(int value) {
        for (PictureStatusCode code : PictureStatusCode.values()) {
            if (code.value == value) {
                return FileUtils.lineToHump(code.name());
            }
        }
        return "";
    }


    /**
     * 根据value返回驼峰文件名
     */
    public static String getTypeByValue(int value) {
        for (PictureStatusCode code : PictureStatusCode.values()) {
            if (value == code.value) {
                return lineToHump(code.name());
            }
        }
        return "";
    }

    /**
     * 将下划线转化驼峰
     */
    private static final Pattern LINEBACKER = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINEBACKER.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 根据value返回驼峰文件名
     */
    public static String getReasonPhraseByValue(int value) {
        for (PictureStatusCode code : PictureStatusCode.values()) {
            if (value == code.value) {
                return code.reasonPhrase;
            }
        }
        return "";
    }

}

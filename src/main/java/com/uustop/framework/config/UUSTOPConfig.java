package com.uustop.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author uustop
 */
@Component
@ConfigurationProperties(prefix = "uustop")
public class UUSTOPConfig {
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 映射路径
     */
    private static String mapperPath;
    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    /** 访问路径 */
    private static String profileUrl;

    private static String reloadImgUrl;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile() {
        return profile;
    }

    public static String getMapperPath() {
        return mapperPath;
    }

    public static void setMapperPath(String mapperPath) {
        UUSTOPConfig.mapperPath = mapperPath;
    }

    public void setProfile(String profile) {
        UUSTOPConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        UUSTOPConfig.addressEnabled = addressEnabled;
    }

    public static String getAvatarPath() {
        return profile + "";
    }

    public static String getDownloadPath() {
        return profile + "download/";
    }
    


    public static String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        UUSTOPConfig.profileUrl = profileUrl;
    }

	public static String getReloadImgUrl() {
		return reloadImgUrl;
	}

	public void setReloadImgUrl(String reloadImgUrl) {
		UUSTOPConfig.reloadImgUrl = reloadImgUrl;
	}
    
    


}

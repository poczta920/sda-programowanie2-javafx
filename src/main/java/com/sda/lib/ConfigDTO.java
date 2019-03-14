package com.sda.lib;

import java.io.Serializable;

public class ConfigDTO implements Serializable {
    private String userName;
    private String localization;
    private int limitCalories;

    public ConfigDTO() {
    }

    public ConfigDTO(String userName, String localization, int limitCalories) {
        this.userName = userName;
        this.localization = localization;
        this.limitCalories = limitCalories;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public int getLimitCalories() {
        return limitCalories;
    }

    public void setLimitCalories(int limitCalories) {
        this.limitCalories = limitCalories;
    }
}

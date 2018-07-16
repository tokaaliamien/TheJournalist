package com.example.android.thejournalist.Models;

/**
 * Created by Toka on 2018-07-14.
 */
public class Country {
    private String name;
    private String code;
    private int flag;

    public Country(String name, String code, int flag) {
        this.name = name;
        this.code = code;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

package com.example.android.thejournalist.Models;

import com.example.android.thejournalist.R;

import java.util.ArrayList;

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

    public static ArrayList<Country> getCountriesList() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Australia", "au", R.drawable.australia));
        countries.add(new Country("Brazil", "br", R.drawable.brazil));
        countries.add(new Country("Canada", "ca", R.drawable.canada));
        countries.add(new Country("China", "cn", R.drawable.china));
        countries.add(new Country("Egypt", "eg", R.drawable.egypt));
        countries.add(new Country("France", "fr", R.drawable.france));
        countries.add(new Country("India", "in", R.drawable.india));
        countries.add(new Country("Italy", "it", R.drawable.italy));
        countries.add(new Country("Japan", "jp", R.drawable.japan));
        countries.add(new Country("USA", "us", R.drawable.united_states_of_america));
        return countries;
    }
}

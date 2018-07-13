package com.example.android.thejournalist.Models;

import java.io.Serializable;

import static com.example.android.thejournalist.Utilites.Helper.compare;

/**
 * Created by Toka on 2018-07-09.
 */

public class Source implements Serializable {
    private String id;
    private String name;

    public Source() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        Source sourceObj = (Source) obj;
        if (!compare(this.getId(), sourceObj.getId()))
            return false;
        else if (!compare(this.getName(), sourceObj.getName()))
            return false;
        else
            return true;
    }
}

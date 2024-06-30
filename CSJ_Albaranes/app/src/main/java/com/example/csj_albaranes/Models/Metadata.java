package com.example.csj_albaranes.Models;

import java.io.Serializable;

public class Metadata implements Serializable {
    public String key;
    public String value;

    public Metadata(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

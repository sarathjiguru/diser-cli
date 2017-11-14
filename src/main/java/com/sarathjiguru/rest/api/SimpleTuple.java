package com.sarathjiguru.rest.api;

/**
 * Created by sarath on 14/11/17.
 */
public class SimpleTuple {

    SimpleTuple() {

    }

    private String key;

    private Object value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

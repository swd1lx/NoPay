package com.example.administrator.nopay.utils;

/**
 * @author swd
 * @date 2017/1/16
 * @time 13:37
 * @description
 */

public class MessageHolder {
    private static MessageHolder ourInstance = new MessageHolder();

    public static MessageHolder getInstance() {
        return ourInstance;
    }

    private String userName;

    private MessageHolder() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

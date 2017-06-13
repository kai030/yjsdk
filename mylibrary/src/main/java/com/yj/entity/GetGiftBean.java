package com.yj.entity;

import java.io.Serializable;

/**
 * Created by Frank on 2017/6/13 0013.
 */

public class GetGiftBean implements Serializable{

    private int code;
    private String msg;
    private String card;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}

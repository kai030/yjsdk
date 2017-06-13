package com.yj.entity;


import java.util.List;

public class GiftListBean {

    private int code;
    private String msg;
    private List<GiftBean> data;

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

    public List<GiftBean> getData() {
        return data;
    }

    public void setData(List<GiftBean> data) {
        this.data = data;
    }
}

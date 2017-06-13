package com.yj.entity;

import java.util.List;

/**
 * Created by Frank on 2017/6/13 0013.
 */

public class NewsListBean {
    private int code;
    private String msg;
    private List<NewsBean> data;

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

    public List<NewsBean> getData() {
        return data;
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
    }
}

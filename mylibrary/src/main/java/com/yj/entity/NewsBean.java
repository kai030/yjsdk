package com.yj.entity;

/**
 * Created by Frank on 2017/6/13 0013.
 */

public class NewsBean {
//    "id":406,"title":"\u6d4b\u8bd5\u65b0\u95fb","pic":"","thumbpic":"","describe":"\u8fd9\u91cc\u662f\u53d9\u8ff0"
//            ,"addtime":"2017-06-10","url":"http:\/\/sdk.5qx.com\/sdk\/article\/news?id=406&gid=1","browse":0

    private int id;
    private String title;
    private String pic;
    private String thumbpic;
    private String describe;
    private String addtime;
    private String url;
    private int browse = -1;//0未读

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getThumbpic() {
        return thumbpic;
    }

    public void setThumbpic(String thumbpic) {
        this.thumbpic = thumbpic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }
}

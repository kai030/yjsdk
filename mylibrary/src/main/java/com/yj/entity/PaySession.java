package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class PaySession extends JsonParseInterface implements Serializable {


    public static int secret = 0;

    public String gameName;
    public String serviceId;
    public String userAccount;
    public String uid = "";
    public String chargeType;
    public String cpOrderId;

    private static final String u_cpOrderId = "cpOrderId";
    private static final String u_gameName = "gameName";//游戏名称
    private static final String u_serviceId = "serviceName";
    private static final String u_userAccount = "username";//账号
    private static final String u_uid = "uid";//uid
    private static final String u_chargeType = "chargeType";


    public PaySession(String gameName, String serviceId, String userAccount, String uid) {
        this.gameName = gameName;
        this.serviceId = serviceId;
        this.userAccount = userAccount;
        this.uid = uid;
        this.cpOrderId = Session.getInstance().cpOrderId;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public JSONObject buildJson() {
        try {
            JSONObject json = new JSONObject();
            setString(json, u_userAccount, userAccount);
            setString(json, u_gameName, gameName);
            setString(json, u_serviceId, serviceId);
            setString(json, u_uid, uid);
            setString(json, u_chargeType, chargeType);
            setString(json,u_cpOrderId,cpOrderId);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void parseJson(JSONObject json) {
    }

    @Override
    public String getShortName() {
        // TODO Auto-generated method stub
        return "pay";
    }

}

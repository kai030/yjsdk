package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class ChargeResult extends JsonParseInterface implements Serializable {

	private static final long serialVersionUID = -8126172668358299629L;

	private static final String u_orderId = "a";
	private static final String u_resultStr = "b";
	private static final String u_partnerId = "c";
	private static final String u_notifyurl = "d";
	private static final String u_goodsName = "e";
	private static final String u_goodsDesc = "f";
	
	/** 充值订单号 */
	public String orderId;
	/** 服务器签名后的字符串  */
	public String resultStr;
	/** */
	public String partnerId;
	/** */
	public String notifyUrl;
	/** */
	public String goodsName;
	/** */
	public String goodsDesc;
	
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setString(json,u_orderId,orderId);
			setString(json,u_resultStr,resultStr);
			setString(json,u_partnerId,partnerId);
			setString(json,u_notifyurl,notifyUrl);
			setString(json, u_goodsName,goodsName);
			setString(json, u_goodsDesc,goodsDesc);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void parseJson(JSONObject json) {
		if (json == null)
			return;
		try {
			orderId = getString(json, u_orderId);
			resultStr = getString(json, u_resultStr);
			partnerId = getString(json, u_partnerId); 
			notifyUrl = getString(json, u_notifyurl);
			goodsName = getString(json, u_goodsName);
			goodsDesc = getString(json, u_goodsDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getShortName() {
		return ShortName.CHARGERESULT;
	}

	@Override
	public String toString() {
		return "";
	}

}

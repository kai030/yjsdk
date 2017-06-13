package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class PayChannel extends JsonParseInterface implements Serializable {

	private static final long serialVersionUID = 404121353502021548L;
	private static final String u_paymentId = "a";
	private static final String u_paymentType = "b";
	private static final String u_paymentName = "c";
	private static final String u_descr = "d";
	private static final String u_notify_url = "e";
	private static final String u_selectMoney = "f";
	private static final String u_notice = "g";
	private static final String u_payMoneyDouble = "h";
	
	/** 支付渠道id */
	public int paymentId;
	/** 支付渠道类型（同类型可能有多个ID） */
	public int paymentType;
	
	/**支付渠道名称 */
	public String paymentName;
	/**充值页面提示语 */
	public String descr;
	/**支付渠道倍数*/
	public int payMoneyDouble ;
	
	/**
	 * 轮播信息
	 */
	public String notice;
	
	/** 提供给第三方支付通道的回调url */
	public String notifyUrl;
	/** 提供用户选择的金额，逗号分隔，单位分  */
	public String selectMoney;
	/** */
	public int serverId;
	
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setInt(json,u_paymentId,paymentId);
			setInt(json,u_paymentType,paymentType);
			setString(json,u_notice,notice);
			setString(json,u_paymentName,paymentName);
			setString(json,u_descr,descr);
			setString(json,u_notify_url,notifyUrl);
			setString(json,u_selectMoney,selectMoney);

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
			paymentId = getInt(json, u_paymentId);
			paymentType = getInt(json, u_paymentType);
			paymentName = getString(json, u_paymentName);
			descr = getString(json, u_descr);
			notifyUrl = getString(json, u_notify_url);
			selectMoney = getString(json, u_selectMoney);
			notice =  getString(json, u_notice);
			payMoneyDouble = getInt(json, u_payMoneyDouble);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getShortName() {

		return ShortName.PAYCHANNELLIST;
	}

	@Override
	public String toString() {
		return "";
	}

	public static Set<Integer> getPayType() {
		Set<Integer> payTypes = new HashSet<Integer>();
		payTypes.add(1); 	// 支付宝
		payTypes.add(2);	
		payTypes.add(3); 	
		return payTypes;
	}

}

package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class PayResult extends JsonParseInterface implements Serializable {

	private static final long serialVersionUID = 4237641041249768791L;

	private static final String u_paymentId = "a";
	private static final String u_orderId = "b";
	private static final String u_orderStatus = "c";
	private static final String u_orderDescr = "d";

	/** 支付方式id */
	public int paymentId;
	/** 支付渠道类型（同类型可能有多个ID） */
	public String paymentType;
	/** 充值订单号 */
	public String orderId;
	/** 订单处理状态，1-成功；0-失败；默认0 */
	public int orderStatus;
	/** 订单状态描述 */
	public String orderDescr;

	
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
		
			setInt(json,u_paymentId,paymentId);
			setString(json,u_orderDescr,orderDescr);
			setInt(json,u_orderStatus,orderStatus);
			setString(json,u_orderId,orderId);
		
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
			orderId = getString(json, u_orderId);
			orderStatus = getInt(json, u_orderStatus);
			orderDescr = getString(json, u_orderDescr);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getShortName() {
		return ShortName.PAYRESULT;
	}

	@Override
	public String toString() {
		return "";
	}

}

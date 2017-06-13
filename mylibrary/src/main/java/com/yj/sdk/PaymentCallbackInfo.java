package com.yj.sdk;

/**
 * @Description: 充值回调信息，设置给Handler里Message的obj
 */
/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class PaymentCallbackInfo {
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_FAILURE = -1;
	
	public int statusCode;
	public String desc;
	public int money;
	@Override
	public String toString() {
		return "PaymentCallbackInfo [statusCode=" + statusCode + ", desc="
				+ desc + ", money=" + money + "]";
	}
}

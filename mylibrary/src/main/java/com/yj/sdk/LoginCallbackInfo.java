package com.yj.sdk;


/**
 * @Description: 登录回调信息，设置给Handler里Message的obj
 * @author Jerry @date 2012-9-12 下午02:03:54
 * @version 1.0
 * @JDK 1.6
 */

public class LoginCallbackInfo {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_FAILURE = -1;
	public static final int STATUS_CLOSE_VIEW = -2;

	public int statusCode;
	public String desc;
//	public String userName;
	public int userId;
	public String loginTime;
	public String sign;

	@Override
	public String toString() {
		return "LoginCallbackInfo [statusCode=" + statusCode + ", desc=" + desc
				+ ", userId=" + userId
				+ ", loginTime=" + loginTime + ", sign=" + sign + "]";
	}
}


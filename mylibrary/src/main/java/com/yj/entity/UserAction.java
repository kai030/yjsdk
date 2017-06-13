package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class UserAction extends JsonParseInterface implements Serializable {
	
	private static final String u_isClieckIcon = "a";
	private static final String u_isCancelLogin = "b";
	
	/** 点击Icon次数  */
	public int isClieckIcon;
	/**点击取消自动登录次数 */
	public int isCancelLogin;

	@Override
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setInt(json, u_isClieckIcon, isClieckIcon);
			setInt(json, u_isCancelLogin, isCancelLogin);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void parseJson(JSONObject json) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getShortName() {
		// TODO Auto-generated method stub
		return ShortName.USERACTION;
	}

}

package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class Result extends JsonParseInterface implements Serializable {
	// 字段key
	private static final String u_code = "code";
	private static final String u_msg = "msg";
	private static final String u_uid = "uid";
	private static final String u_username = "username";
	private static final String u_password = "password";

	/**登录状态，0-成功;
	-1 失败，默认使用resultDescr提示;
	-2 失败，使用resultMsg提示用户; **/
	public int resultCode = -1;
	public String resultDescr;
	public static String sessionId;
	public  int uid;
	public  String  username;
	public String password;


	
	@Override
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setInt(json, u_code, resultCode);
			setString(json, u_msg, resultDescr);
			setInt(json, u_uid, uid);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void parseJson(JSONObject json) {
		if (json == null) 
			return ;
		try {
			try {
				resultCode = getInt(json, u_code);
			}catch (Exception e){}

			try {
				uid = getInt(json, u_uid);
			}catch (Exception e){}

			try {
				username = getString(json, u_username);
			}catch (Exception e){}

			try {
				password = getString(json, u_password);
			}catch (Exception e){}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getShortName() {
		// TODO Auto-generated method stub
		return ShortName.RESULT;
	}

}
